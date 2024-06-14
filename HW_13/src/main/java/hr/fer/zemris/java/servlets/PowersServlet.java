package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet generates an .xls file that contains numbers from a to b and
 * their n-th powers.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a, b, n;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));	
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("errorMessage", "Invalid number format.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (a > 100 || a < -100 || b > 100 || b < -100 || n < 1 || n > 5) {
			req.getSession().setAttribute("errorMessage", "Given attributes are out of bounds.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}	
		if (a > b) {
			a ^= b;
			b ^= a;
			a ^= b;
		}	
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=potencije.xls");
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);
			HSSFRow rowHead = sheet.createRow(0);
			rowHead.createCell(0).setCellValue("Number");
			String power = getNumber(i);
			rowHead.createCell(1).setCellValue(power + " power");
			for (int k = 1, j = a; j < b; j++, k++) {
				HSSFRow row = sheet.createRow(k);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

	/**
	 * Returns ordinal number string from provided number.
	 * 
	 * @param i
	 *            number to turn into string
	 * @return Ordinal number string
	 */
	private String getNumber(int i) {
		switch (i) {
		case 1:
			return "1st";
		case 2:
			return "2nd";
		case 3:
			return "3rd";
		default:
			return i + "th";
		}
	}
}