package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * The {@code Init} class is a listener that listens for the application start
 * and creates necessary data for the application, connects to the database that
 * already has to exist and creates tables in it if they don't exist and fills
 * them with data.
 * 
 * @author Karlo Bencic
 * 
 */
@WebListener
public class Init implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Properties prop = new Properties();
			String propertiesPath = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
			prop.load(Files.newInputStream(Paths.get(propertiesPath)));
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String dbName = prop.getProperty("name");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
					+ password;
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException ex) {
				throw new RuntimeException("Error occurred during pool initialization.", ex);
			}
			cpds.setJdbcUrl(connectionURL);
			try {
				DAOProvider.getDao().createPoll(cpds);
				DAOProvider.getDao().createOptions(cpds);
				if (DAOProvider.getDao().isTableEmpty(cpds, "Polls")) {
					DAOProvider.getDao().fillTables(cpds,
							sce.getServletContext().getRealPath("/WEB-INF/ankete-definicija.txt"));
				}
			} catch (Exception e) {
				System.out.println("Failed to create tables");
				e.printStackTrace();
				System.exit(1);
			}
			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds == null) {
			return;
		}
		try {
			DataSources.destroy(cpds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}