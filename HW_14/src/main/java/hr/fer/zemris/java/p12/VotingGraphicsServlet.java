package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The {@code VotingGraphicsServlet} servlet creates a png chart of votes and
 * provides it to action caller.
 * 
 * @author Karlo Bencic
 * 
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-grafika" })
public class VotingGraphicsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		resp.setContentType("image/png");

		List<PollOption> options = DAOProvider.getDao().getPollOptions((Long) req.getSession().getAttribute("pollID"));
		JFreeChart chart = ChartFactory.createPieChart3D("Votes", createDataset(options), true, true, false);
		ChartUtilities.writeChartAsPNG(out, chart, 400, 400);
	}

	/**
	 * Creates the dataset with the given list of options.
	 *
	 * @param options
	 *            the options
	 * @return the pie dataset
	 */
	private PieDataset createDataset(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();
		options.stream().forEach(op -> result.setValue(op.getOptionTitle(), op.getVotesCount()));
		return result;
	}
}