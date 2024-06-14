package hr.fer.zemris.java.p12.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author Karlo Bencic
 */
public class SQLDAO implements DAO {
	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls ORDER BY id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setID(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("An error occurred while fetching polls.", ex);
		}
		return polls;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"SELECT id, optiontitle, optionlink, pollid, votescount FROM PollOptions WHERE pollid=?");
			pst.setLong(1, Long.valueOf(pollID));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();
						option.setID(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollID(rs.getLong(4));
						option.setVotesCount(rs.getLong(5));
						options.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("An error occurred while fetching poll options.", ex);
		}
		return options;
	}

	@Override
	public void updatePoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votescount=votescount+1 WHERE id=?");
			pst.setLong(1, id);

			int numberOfAffectedRows = pst.executeUpdate();
			System.out.println("Number of rows affected: " + numberOfAffectedRows);
		} catch (Exception ex) {
			throw new DAOException(ex);
		} finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls WHERE id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll();
						poll.setID(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("An error occurred while fetching poll.", ex);
		}
		return poll;
	}

	@Override
	public void createOptions(DataSource cpds) throws SQLException {
		Connection con = cpds.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"CREATE TABLE PollOptions ("
					+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, optionTitle VARCHAR(100) NOT NULL, "
					+ "optionLink VARCHAR(150) NOT NULL, pollID BIGINT, votesCount BIGINT, "
					+ "FOREIGN KEY (pollID) REFERENCES Polls(id)"
					+ ")");
			pst.executeUpdate();
		} catch (SQLException e) {
			if (!tableAlreadyExists(e)) {
				throw e;
			}
		} finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public void createPoll(DataSource cpds) throws SQLException {
		Connection con = cpds.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"CREATE TABLE Polls ("
					+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ "title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL"
					+ ")");
			pst.executeUpdate();
		} catch (SQLException e) {
			if (!tableAlreadyExists(e)) {
				throw e;
			}
		} finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public boolean isTableEmpty(DataSource cpds, String tableName) throws SQLException {
		Connection con = cpds.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT COUNT(*) as MY_COUNT FROM " + tableName);
			ResultSet rset = pst.executeQuery();
			rset.next();
			int number = rset.getInt("MY_COUNT");
			return number > 0 ? false : true;
		} finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public void fillTables(DataSource cpds, String path) throws IOException, SQLException {
		Path p = Paths.get(path);
		List<String> lines = Files.readAllLines(p);
		Connection con = cpds.getConnection();
		for (String line : lines) {
			String[] pair = line.split("\\t");
			try (PreparedStatement pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
				pst.setString(1, pair[0]);
				pst.setString(2, pair[1]);
				pst.executeUpdate();
				ResultSet rset = pst.getGeneratedKeys();
				try {
					if (rset != null && rset.next()) {
						long noviID = rset.getLong(1);
						String pt = path.substring(0, path.lastIndexOf("\\") + 1) + pair[2];
						insertPollOptions(con, noviID, pt);
					}
				} finally {
					try {
						rset.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				pst.clearParameters();
			}
		}
	}

	/**
	 * Inserts poll options from the sample data file.
	 *
	 * @param con
	 *            the connection
	 * @param id
	 *            the poll id
	 * @param path
	 *            the path to file
	 * @throws IOException
	 *             signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             signals that an SQL exception has occurred.
	 */
	private void insertPollOptions(Connection con, long id, String path) throws IOException, SQLException {
		Path p = Paths.get(path);
		List<String> lines = Files.readAllLines(p);
		for (String line : lines) {
			String[] pair = line.split("\\t");
			System.out.println(Arrays.toString(pair));
			try (PreparedStatement pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,0)")) {
				pst.setString(1, pair[1]);
				pst.setString(2, pair[2]);
				pst.setLong(3, id);
				pst.executeUpdate();
				pst.clearParameters();
			}
		}
	}
	
	/**
	 * Checks if the table already exists in the database.
	 *
	 * @param e
	 *            the sql exception
	 * @return true, if exists
	 */
	private boolean tableAlreadyExists(SQLException e) {
		return e.getSQLState().equals("X0Y32");
	}
}