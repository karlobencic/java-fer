package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The {@code VotingXlsServlet} servlet creates a downloadable .xls file
 * containing voting results.
 * 
 * @author Karlo Bencic
 * 
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-xls" })
public class VotingXlsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=voting-results.xls");

		List<PollOption> results = DAOProvider.getDao().getPollOptions((Long) req.getSession().getAttribute("pollID"));

		HSSFWorkbook hwb = createXls(results);
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

	/**
	 * Creates the xls file with the given list of results.
	 *
	 * @param results
	 *            the results
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createXls(List<PollOption> results) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("New sheet");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");

		int i = 1;
		for (PollOption option : results) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
			i++;
		}
		return hwb;
	}
}