package hr.fer.zemris.java.servlets;

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

/**
 * This servlet generates a simple pie chart with voting results data.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-grafika" })
public class VotingChartServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURL().toString().endsWith("glasanje-rezultati")) {
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		} else {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("image/png");
			List<Band> results = (List<Band>) req.getSession().getAttribute("results");
			JFreeChart chart = ChartFactory.createPieChart3D("", createDataset(results), true, true, false);
			ChartUtilities.writeChartAsPNG(out, chart, 400, 300);
		}
	}

	/**
	 * Creates the dataset.
	 *
	 * @return the pie dataset
	 */
	private PieDataset createDataset(List<Band> data) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (Band band : data) {
			result.setValue(band.getName(), band.getVotes());
		}
		return result;
	}

}