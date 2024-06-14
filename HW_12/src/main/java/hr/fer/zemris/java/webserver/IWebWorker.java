package hr.fer.zemris.java.webserver;

/**
 * The {@code IWebWorker} interface is implemented by worker classes that are
 * invoked by the server after the proper request asking for that operation has
 * been received. Server routes the work to the class that takes the context and
 * executes its job and delivers the result to client.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IWebWorker {

	/**
	 * Process the received request and extract all the necessary info from it
	 * and execute the action and deliver the result to client.
	 *
	 * @param context
	 *            the context with data to do a job
	 * @throws Exception
	 *             if an error occurred exception
	 */
	void processRequest(RequestContext context) throws Exception;
}
