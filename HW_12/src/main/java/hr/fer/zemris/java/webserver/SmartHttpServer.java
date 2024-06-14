package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The {@code SmartHttpServer} class is an HTTP server that uses TCP transfer
 * protocol that works in a way that it transfers bytes between server and
 * client using {@link Socket}. This program is started from the command-line.
 * The server is stopped by the 'stop' command.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartHttpServer {

	/** The server address. */
	private String address;

	/** The server port. */
	private int port;

	/** The worker threads. */
	private int workerThreads;

	/** The session timeout. */
	private int sessionTimeout;

	/** The mime types. */
	private final Map<String, String> mimeTypes = new HashMap<>();

	/** The server thread. */
	private ServerThread serverThread;

	/** The thread pool of the server. */
	private ExecutorService threadPool;

	/** The document root of the server. */
	private Path documentRoot;

	/** The web workers map. */
	private Map<String, IWebWorker> workersMap;

	/** The sessions. */
	private final Map<String, SessionMapEntry> sessions = new HashMap<>();

	/** The session generator. */
	private final Random sessionRandom = new Random();

	/** The session collector of the server. */
	private final SessionCollector sc = new SessionCollector();

	/** The disallowed characters for cookie name and value. */
	private final Pattern cookiePattern = Pattern.compile("[\\[\\]\\(\\)=,\"\\/?@:;]+");

	/**
	 * The {@code SessionMapEntry} class represents a data binded to session.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class SessionMapEntry {

		/** The session id. */
		String sid;

		/** The time when this session expires. */
		long validUntil;

		/** The parameters of this session. */
		Map<String, String> map;
	}

	/**
	 * Instantiates a new smart http server.
	 *
	 * @param configFileName
	 *            the config file name
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load config file");
		}
		loadProperties(properties);
	}

	/**
	 * Loads server properties from the server config file.
	 *
	 * @param properties
	 *            the properties
	 */
	private void loadProperties(Properties properties) {
		address = properties.getProperty(PropertyKeys.ADDRESS);
		port = Integer.parseInt(properties.getProperty(PropertyKeys.PORT));
		workerThreads = Integer.parseInt(properties.getProperty(PropertyKeys.WORKER_THREADS));
		sessionTimeout = Integer.parseInt(properties.getProperty(PropertyKeys.SESSION_TIMEOUT));
		documentRoot = Paths.get(properties.getProperty(PropertyKeys.DOCUMENT_ROOT));

		String workersConfigPath = properties.getProperty(PropertyKeys.WORKERS_CONFIG);
		String mimeTypesPath = properties.getProperty(PropertyKeys.MIME_CONFIG);

		loadMimeTypes(mimeTypesPath);
		loadWorkers(workersConfigPath);
	}

	/**
	 * Loads mime types from the config file.
	 *
	 * @param mimeTypesPath
	 *            the mime types config file path
	 */
	private void loadMimeTypes(String mimeTypesPath) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(mimeTypesPath)));
			for (Object key : properties.keySet()) {
				mimeTypes.put(key.toString(), properties.get(key).toString());
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load mime config file");
		}
	}

	/**
	 * Loads workers from the config file.
	 *
	 * @param workersConfigPath
	 *            the workers config file path
	 */
	private void loadWorkers(String workersConfigPath) {
		Properties properties = new Properties();
		workersMap = new HashMap<>();
		try {
			properties.load(Files.newInputStream(Paths.get(workersConfigPath)));
			for (Object key : properties.keySet()) {
				String path = key.toString();
				String fqcn = properties.get(key).toString();
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load workers config file");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Must provide a single argument: path to server.properties.");
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.nextLine().equals("stop")) {
				break;
			}
		}
		server.stop();
		sc.close();
	}

	/**
	 * Starts the server.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			sc.setDaemon(true);
		}
		if (!serverThread.isAlive()) {
			serverThread.start();
			sc.start();
		}
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		threadPool.shutdown();
	}

	/**
	 * The {@code ServerThread} class represents a thread that waits for
	 * requests and serves proper server responses.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	protected class ServerThread extends Thread {

		/** The running flag used to shut down the server. */
		private volatile boolean running = true;

		/**
		 * Terminates the server thread.
		 */
		public void terminate() {
			running = false;
		}

		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				serverSocket.setSoTimeout(2500);
				while (running) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (SocketTimeoutException ignored) {
					}
				}
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The {@code SessionCollector} class periodically iterates through all
	 * sessions and deletes timed-out sessions in order to avoid excessive
	 * memory consumption by expired sessions.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class SessionCollector extends Thread {

		/** The Constant PERIOD of scan. */
		private static final int PERIOD = 300000;

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(PERIOD);
					synchronized (sessions) {
						sessions.entrySet()
								.removeIf(entry -> entry.getValue().validUntil < (System.currentTimeMillis() / 1000));
					}
				} catch (InterruptedException ignored) {
				}
			}
		}
	}

	/**
	 * The {@code ClientWorker} class is dispatched every time a new request is
	 * sent to a server, server then forwards the request to client thread.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** The client socket. */
		private final Socket csocket;

		/** The input stream. */
		private PushbackInputStream istream;

		/** The output stream. */
		private OutputStream ostream;

		/** The HTTP version. */
		private String version;

		/** The request method. */
		private String method;

		/** The parameters. */
		private final Map<String, String> params = new HashMap<>();

		/** The temporary parameters. */
		private final Map<String, String> tempParams = new HashMap<>();

		/** The permanent parameters. */
		private Map<String, String> permParams = new HashMap<>();

		/** The output cookies. */
		private final List<RCCookie> outputCookies = new ArrayList<>();

		/** The session id. */
		private String SID;

		/** The host address. */
		private String hostAddress;

		/** The request. */
		private List<String> request;

		/** The context. */
		private RequestContext context;

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket
		 *            the csocket
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				request = readRequest();

				if (request == null || request.size() < 1) {
					sendError(400, "Bad request");
					return;
				}
				String firstLine = request.get(0);
				String[] info = firstLine.split(" ");
				if (checkInvalidHeader(info)) {
					return;
				}			

				String[] reqPath = info[1].split("\\?");
				String path = reqPath[0].substring(1);
				if (reqPath.length > 1) {
					String paramString = reqPath[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Checks for address in the header.
		 *
		 * @param request
		 *            the request
		 */
		private void checkForAddress(List<String> request) {
			for (String line : request) {
				if (!line.startsWith("Host:"))
					continue;
				hostAddress = line.substring("Host:".length(), line.lastIndexOf(":")).trim();
				break;
			}
		}

		/**
		 * Checks for invalid header.
		 *
		 * @param info
		 *            the info
		 * @return true, if successful
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private boolean checkInvalidHeader(String[] info) throws IOException {
			if (info.length != 3) {
				sendError(400, "Bad request");
				return true;
			}
			method = info[0].toUpperCase();
			if (!method.equals("GET")) {
				sendError(405, "Method not allowed");
				return true;
			}
			version = info[2].toUpperCase();
			if (!version.equals("HTTP/1.1")) {
				sendError(505, "HTTP version not supported");
				return true;
			}
			return false;
		}

		/**
		 * Checks for cookies if they have been set by the request.
		 *
		 * @param request
		 *            the request
		 * @param path
		 *            the path
		 */
		private void checkForCookies(List<String> request, String path) {
			String sidCandidate = "";
			for (String headerLine : request) {
				if (!headerLine.startsWith("Cookie:"))
					continue;
				headerLine = headerLine.substring("Cookie:".length()).trim();
				String[] cookies = headerLine.split(";");
				for (String cooky : cookies) {
					String[] pair = cooky.split("=");
					if (pair.length != 2)
						continue;
					String name = pair[0].trim();
					String value = pair[1].trim();
					if (invalidCookie(name, value))
						continue;
					if (name.equals("sid")) {
						sidCandidate = value;
					} else {
						RCCookie cookie = new RCCookie(name, value, null, hostAddress, "/" + path);
						outputCookies.add(cookie);
					}
				}
			}
			checkSession(sidCandidate);
		}

		/**
		 * Checks if an active session is associated with a {@code sessionID}
		 * withing the cokkies.
		 *
		 * @param sidCandidate
		 *            the session id candidate
		 */
		private synchronized void checkSession(String sidCandidate) {
			if (sidCandidate.isEmpty() || !sessions.containsKey(sidCandidate)) {
				String newSID = SID = sidGenerator();
				createEntry(newSID);
			} else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry.validUntil < (System.currentTimeMillis() / 1000)) {
					sessions.remove(entry);
					String newSID = SID = sidGenerator();
					createEntry(newSID);
				} else {
					SID = sidCandidate;
					entry.validUntil = (System.currentTimeMillis() / 1000) + sessionTimeout;
				}
			}
			permParams = sessions.get(SID).map;
		}

		/**
		 * Creates the entry that holds relevant information about the session.
		 *
		 * @param newSessionID
		 *            the new session ID
		 */
		private void createEntry(String newSessionID) {
			SessionMapEntry ret = new SessionMapEntry();
			ret.sid = newSessionID;
			ret.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			ret.map = new ConcurrentHashMap<>();
			ret.map.put("sid", ret.sid);
			for (RCCookie currentCookie : outputCookies) {
				ret.map.put(currentCookie.getName(), currentCookie.getValue());
			}
			sessions.put(newSessionID, ret);
			outputCookies.add(new RCCookie("sid", newSessionID, null, hostAddress, "/", true));
		}

		/**
		 * Session id generator, 20 random characters.
		 *
		 * @return the new session ID
		 */
		private String sidGenerator() {
			char[] rand = new char[20];
			for (int i = 0; i < rand.length; i++) {
				rand[i] = (char) (65 + sessionRandom.nextInt(26));
			}
			return new String(rand);
		}

		/**
		 * Checks if the cookie is invalid.
		 *
		 * @param name
		 *            the name
		 * @param value
		 *            the value
		 * @return true, if cookie is invalid
		 */
		private boolean invalidCookie(String name, String value) {
			return cookiePattern.matcher(name).find() || cookiePattern.matcher(value).find();
		}

		/**
		 * Sends the error response from the server.
		 *
		 * @param statusCode
		 *            the status code
		 * @param statusText
		 *            the status text
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			try {
				ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: Smart HTTP server\r\n"
						+ "Content-Type: text/html;charset=UTF-8\r\n" + "Content-Length: 0\r\n"
						+ "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII));
				ostream.flush();
			} catch (SocketException e) {
				throw new IOException(e.getMessage());
			}
		}

		/**
		 * Parses the request parameters.
		 *
		 * @param paramString
		 *            the parameters string
		 */
		private void parseParameters(String paramString) {
			String[] groups = paramString.split("&");
			for (String group : groups) {
				String[] nameVal = group.trim().split("=");
				params.put(nameVal[0].trim(), nameVal[1].trim());
			}
		}

		/**
		 * Reads the http request header and returns all lines.
		 *
		 * @return the request header lines as list
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private List<String> readRequest() throws IOException {
			byte[] request = readRequest(istream);
			if (request == null) {
				return null;
			}
			String requestHeader = new String(request, StandardCharsets.US_ASCII);
			List<String> headers = new ArrayList<>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (currentLine != null && !currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Reads the http request as bytes.
		 *
		 * @param is
		 *            the input stream
		 * @return the byte[] array of all the read bytes
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private byte[] readRequest(PushbackInputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			loop: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break loop;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break loop;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Internal dispatch request handles the request depending if if came
		 * from the server or from the client.
		 *
		 * @param urlPath
		 *            the url path
		 * @param directCall
		 *            the direct call flag
		 * @throws Exception
		 *             the exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			Path requestedPath = documentRoot.resolve(urlPath);
			String mimeType = "application/octet-stream";
			String rp = requestedPath.toString();
			String ext = rp.substring(rp.lastIndexOf('.') + 1, rp.length());

			checkForAddress(request);
			checkForCookies(request, urlPath);
			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			}
			if (directCall && (urlPath.equals("/private") || urlPath.startsWith("/private/"))) {
				sendError(404, "File not found");
			}
			if (urlPath.startsWith("ext/")) {
				String className = urlPath.substring("ext/".length());
				String fqcn = "hr.fer.zemris.java.webserver.workers." + className;
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(context);
			} else if (workersMap.containsKey("/" + urlPath)) {
				workersMap.get("/" + urlPath).processRequest(context);
			} else if (directCall && !requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden!");
			} else if (directCall && (!Files.exists(requestedPath) || !Files.isReadable(requestedPath))) {
				sendError(404, "File not found");
			} else if (ext.equals("smscr")) {
				if (!directCall) {
					requestedPath = Paths.get(documentRoot.toString(), urlPath);
				}
				String documentBody = new String(Files.readAllBytes(requestedPath));
				new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
			} else {
				String mime = mimeTypes.get(ext);
				mimeType = mime == null ? mimeType : mime;
				byte[] data = Files.readAllBytes(requestedPath);
				context.addAdditionalHeader("Content-Length: " + data.length);
				context.setMimeType(mimeType);
				context.setStatusCode(200);
				context.write(data);
			}
		}
	}
}
