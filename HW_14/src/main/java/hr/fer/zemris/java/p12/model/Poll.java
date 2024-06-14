package hr.fer.zemris.java.p12.model;

/**
 * The {@code Poll} model is a java bean which contains all fields that
 * are described in the database from the {@code Poll} table.
 * 
 * @author Karlo Bencic
 * 
 */
public class Poll {

	/** The id. */
	private long ID;
	
	/** The title. */
	private String title;
	
	/** The message. */
	private String message;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Sets the id.
	 *
	 * @param iD
	 *            the new id
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}