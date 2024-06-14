package hr.fer.zemris.java.p12.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Karlo Bencic
 *
 */
public interface DAO {

	/**
	 * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka: id i
	 * title.
	 * 
	 * @return listu unosa
	 * @throws DAOException
	 *             u slučaju pogreške
	 */
	List<Poll> getPolls() throws DAOException;

	/**
	 * Dohvaća Bend za zadani id. Ako unos ne postoji, vraća <code>null</code>.
	 *
	 * @param pollID
	 *            the poll ID
	 * @return bend iz baze
	 * @throws DAOException
	 *             the DAO exception
	 */
	List<PollOption> getPollOptions(long pollID) throws DAOException;

	/**
	 * Uvećava broj glasova nekog entry-ja u anketi za 1.
	 *
	 * @param id
	 *            Id entry-ja kojemu treba uvećati glas
	 * @throws DAOException
	 *             the DAO exception
	 */
	void updatePoll(long id) throws DAOException;

	/**
	 * Dohvaća jednu anketu vezanu na proslijeđeni ID.
	 *
	 * @param id
	 *            id ankete
	 * @return Anketa
	 * @throws DAOException
	 *             the DAO exception
	 */
	Poll getPoll(long id) throws DAOException;

	/**
	 * Stvara tablicu sa opcijama za pojedinu anketu.
	 *
	 * @param cpds
	 *            the cpds
	 * @throws SQLException
	 *             the SQL exception
	 */
	void createOptions(DataSource cpds) throws SQLException;

	/**
	 * Stvara tablicu anketa.
	 *
	 * @param cpds
	 *            izvor podataka
	 * @throws SQLException
	 *             the SQL exception
	 */
	void createPoll(DataSource cpds) throws SQLException;

	/**
	 * Provjerava je li tablica u bazi prazna
	 *
	 * @param cpds
	 *            izvor podataka
	 * @param tableName
	 *            ime tablice
	 * @return true, ako je prazna
	 * @throws SQLException
	 *             the SQL exception
	 */
	boolean isTableEmpty(DataSource cpds, String tableName) throws SQLException;

	/**
	 * Puni tablice sa podacima iz .txt datoteke do koje je potrebno poslati
	 * puteljak
	 *
	 * @param cpds
	 *            izvor podataka
	 * @param path
	 *            puteljak do datoteke
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the SQL exception
	 */
	void fillTables(DataSource cpds, String path) throws IOException, SQLException;
}