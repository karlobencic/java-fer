package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * The {@code JPAEMProvider} class is a JPA entity manager provider that
 * provides access to database and enables writing to database thread safely and
 * locally.
 * 
 * @author Karlo Bencic
 * 
 */
public class JPAEMProvider {

	/** The locals. */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager for this thread.
	 *
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Commits and closes the entity manager bound to this thread and removes it
	 * from threadlocal map.
	 *
	 * @throws DAOException
	 *             the DAO exception
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * The {@code LocalData} struct that contains an entity manager.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class LocalData {

		/** The entity manager. */
		EntityManager em;
	}
}