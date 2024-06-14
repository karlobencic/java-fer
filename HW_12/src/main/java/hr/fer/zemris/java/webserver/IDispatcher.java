package hr.fer.zemris.java.webserver;

/**
 * The {@code IDispatcher} interface is used to dispatch the server request from
 * the client with the given url path.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IDispatcher {

	/**
	 * Dispatches the request which happens after the parameter extraction and
	 * isolation of requested {@code urlPath}.
	 *
	 * @param urlPath
	 *            the url path
	 * @throws Exception
	 *             the exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
