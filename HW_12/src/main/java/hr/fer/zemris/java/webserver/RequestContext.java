package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code RequestContext} class encapsulates information about an HTTP
 * request that matches a defined route. This class contains information about
 * the HTTP request in the HttpContext property.
 * 
 * @author Karlo Bencic
 * 
 */
public class RequestContext {

	/**
	 * The {@code RCCookie} class represents a request context cookie.
	 * 
	 * @author Karlo Bencic
	 */
	public static class RCCookie {

		/** The name of this cookie. */
		private final String name;

		/** The value of this cookie. */
		private final String value;

		/** The domain where the cookie is used. */
		private final String domain;

		/** The path where this cookie is used. */
		private final String path;

		/** The max age of a cookie. */
		private final Integer maxAge;

		/** The httpOnly flag, true for httpOnly cookies. */
		private boolean httpOnly;

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name
		 *            the name
		 * @param value
		 *            the value
		 * @param maxAge
		 *            the max age
		 * @param domain
		 *            the domain
		 * @param path
		 *            the path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this(name, value, maxAge, domain, path, false);
		}

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name
		 *            the name
		 * @param value
		 *            the value
		 * @param maxAge
		 *            the max age
		 * @param domain
		 *            the domain
		 * @param path
		 *            the path
		 * @param httpOnly
		 *            the http only
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			if (name == null || value == null) {
				throw new IllegalArgumentException("Invalid cookie");
			}
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain.
		 *
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path.
		 *
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age.
		 *
		 * @return the max age
		 */
		public int getMaxAge() {
			return maxAge;
		}
	}

	/** The output stream. */
	private OutputStream outputStream;

	/** The charset of request. */
	private Charset charset;

	/** The encoding of request. */
	private String encoding = "UTF-8";

	/** The status code. */
	private int statusCode = 200;

	/** The status text. */
	private String statusText = "OK";

	/** The mime type. */
	private String mimeType = "text/html";

	/** The parameters of request. */
	private final Map<String, String> parameters;

	/** The temporary parameters of request. */
	private Map<String, String> temporaryParameters;

	/** The persistent parameters of request. */
	private Map<String, String> persistentParameters;

	/** The output cookies. */
	private List<RCCookie> outputCookies;

	/** The header generated flag. */
	private boolean headerGenerated;

	/** The additional headers. */
	private List<String> additionalHeaders;

	/** The dispatcher. */
	private IDispatcher dispatcher;

	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param parameters
	 *            the parameters
	 * @param persistentParameters
	 *            the persistent parameters
	 * @param outputCookies
	 *            the output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream cannot be null");
		}
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param parameters
	 *            the parameters
	 * @param persistentParameters
	 *            the persistent parameters
	 * @param outputCookies
	 *            the output cookies
	 * @param temporaryParameters
	 *            the temporary parameters
	 * @param dispatcher
	 *            the dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Gets the parameter.
	 *
	 * @param name
	 *            the name
	 * @return the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Gets the parameter names.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Gets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Gets the persistent parameter names.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Sets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Gets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Gets the temporary parameter names.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Sets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			temporaryParameters = new HashMap<>();
		}
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the temporary parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes a provided data to ouputstream
	 *
	 * @param data
	 *            the data
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			charset = Charset.forName(encoding);
			generateHeader();
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Writes a provided text to ouputstream
	 *
	 * @param text
	 *            the text
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		charset = Charset.forName(encoding);
		return write(text.getBytes(charset));
	}

	/**
	 * Method that generates the header.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void generateHeader() throws IOException {
		outputStream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Content-Type:" + mimeType
				+ (mimeType.trim().startsWith("text/") ? "; charset=UTF-8" : "") + "\r\n"
				+ (additionalHeaders != null ? getAdditionalHeaders() : "")
				+ (!outputCookies.isEmpty() ? generateCookie() : "") + "\r\n").getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();
		headerGenerated = true;
	}

	/**
	 * Gets the additional headers.
	 *
	 * @return the additional headers
	 */
	private String getAdditionalHeaders() {
		StringBuilder sb = new StringBuilder();
		for (String header : additionalHeaders) {
			sb.append(header).append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * Generates the cookie.
	 *
	 * @return the string
	 */
	private String generateCookie() {
		StringBuilder sb = new StringBuilder();
		for (RCCookie r : outputCookies) {
			sb.append("Set-Cookie: ").append(r.name).append("=").append(r.value);
			if (r.domain != null) {
				sb.append("; Domain=").append(r.domain);
			}
			if (r.path != null) {
				sb.append("; Path=").append(r.path);
			}
			if (r.maxAge != null) {
				sb.append("; Max-Age=").append(r.maxAge.toString());
			}
			if (r.httpOnly) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * Sets the encoding.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText
	 *            the new status text
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *            the new mime type
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Adds the RC cookie.
	 *
	 * @param rcCookie
	 *            the rc cookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

	/**
	 * Gets the dispatcher.
	 *
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Adds the additional header.
	 *
	 * @param header
	 *            the header
	 */
	public void addAdditionalHeader(String header) {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated");
		}
		if (additionalHeaders == null) {
			additionalHeaders = new ArrayList<>();
		}
		additionalHeaders.add(header);
	}
}
