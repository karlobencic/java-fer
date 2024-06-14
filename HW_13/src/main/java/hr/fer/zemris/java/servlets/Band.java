package hr.fer.zemris.java.servlets;

/**
 * This is a java bean for a Band.
 * 
 * @author Karlo Bencic
 */
public class Band {

	/** The band id. */
	private String id;

	/** The band name. */
	private String name;

	/** The link to band page. */
	private String link;

	/** The votes band has collected. */
	private int votes;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link
	 *            the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Gets the votes.
	 *
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Sets the votes.
	 *
	 * @param votes
	 *            the new votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
}