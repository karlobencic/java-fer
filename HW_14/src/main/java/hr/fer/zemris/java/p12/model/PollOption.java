package hr.fer.zemris.java.p12.model;

/**
 * The {@code PollOption} model is a java bean which contains all fields that
 * are described in the database from the {@code PollOption} table.
 * 
 * @author Karlo Bencic
 * 
 */
public class PollOption {

	/** The id. */
	private long id;

	/** The option title. */
	private String optionTitle;

	/** The option link. */
	private String optionLink;

	/** The poll ID. */
	private long pollID;

	/** The votes count. */
	private long votesCount;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getID() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setID(long id) {
		this.id = id;
	}

	/**
	 * Gets the option title.
	 *
	 * @return the option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title.
	 *
	 * @param optionTitle
	 *            the new option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link.
	 *
	 * @param optionLink
	 *            the new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Gets the poll ID.
	 *
	 * @return the poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll ID.
	 *
	 * @param pollID
	 *            the new poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votes count.
	 *
	 * @param votesCount
	 *            the new votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}