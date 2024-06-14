package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The {@code SumWorker} class is a worker that takes the provided request and
 * looks for two parameters {@code a} and {@code b}. If they are not set or not
 * integers, default values will be used, a=1 and b=2. The parameters are sent
 * to calc.smscr script which then calculates the sum of them and renders the
 * HTML table of contents.
 * 
 * @author Karlo Bencic
 * 
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		try {
			context.write("<h1>Zbrajanje</h1>");

			int a, b;
			try {
				a = Integer.parseInt(context.getParameter("a"));
			} catch (NumberFormatException e) {
				a = 1;
			}
			try {
				b = Integer.parseInt(context.getParameter("b"));
			} catch (NumberFormatException e) {
				b = 2;
			}

			context.setTemporaryParameter("a", String.valueOf(a));
			context.setTemporaryParameter("b", String.valueOf(b));
			context.setTemporaryParameter("zbroj", String.valueOf(a + b));

			try {
				context.getDispatcher().dispatchRequest("/private/calc.smscr");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
