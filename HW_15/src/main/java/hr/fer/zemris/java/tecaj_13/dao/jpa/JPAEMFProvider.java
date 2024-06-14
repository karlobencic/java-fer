package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The {@code JPAEMFProvider} class is an entity manager factory provider for
 * JPA entity manager. This class only wraps the single application entity
 * manager factory.
 * 
 * @author Karlo Bencic
 * 
 */
public class JPAEMFProvider {

	/** The emf. */
	public static EntityManagerFactory emf;

	/**
	 * Gets the entity manager factory.
	 *
	 * @return the emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the entity manager factory.
	 *
	 * @param emf
	 *            the new emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}