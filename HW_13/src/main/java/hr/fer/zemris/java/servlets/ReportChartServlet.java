package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

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
 * This servlet generates a simple pie chart with OS usage data.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/report", "/showreport" })
public class ReportChartServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURL().toString().endsWith("showreport")) {
			req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
		} else {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("image/png");
			JFreeChart chart = ChartFactory.createPieChart3D("OS usage", createDataset(), true, true, false);
			ChartUtilities.writeChartAsPNG(out, chart, 400, 300);
		}
	}

	/**
	 * Creates the dataset.
	 *
	 * @return the pie dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Android", 38.85);
		result.setValue("Windows", 36.96);
		result.setValue("iOS", 13.42);
		result.setValue("MacOS", 5.02);
		result.setValue("Linux", 0.77);
		result.setValue("Unknown", 2.88);
		return result;
	}

}