package hr.fer.zemris.java.servlets;

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

/**
 * This servlet generates an .xls file that contains voting results data.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class VotingXlsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> results = (List<Band>) req.getSession().getAttribute("results");

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Sheet 1");
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Bend");
		rowHead.createCell(1).setCellValue("Broj glasova");

		for (int i = 1; i < results.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			Band band = results.get(i - 1);
			row.createCell(0).setCellValue(band.getName());
			row.createCell(1).setCellValue(band.getVotes());
		}
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}