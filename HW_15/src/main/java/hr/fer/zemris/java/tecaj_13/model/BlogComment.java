package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.*;

/**
 * The {@code BlogComment} is a model class for blog comments. Each comment is
 * connected with a {@link BlogEntry} that it belongs to.
 * 
 * @author Karlo Bencic
 * 
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/** The comment id. */
	private Long id;

	/** The blog entry that this comment is associated with. */
	private BlogEntry blogEntry;

	/** The users e-mail. */
	private String usersEMail;

	/** The comment message. */
	private String message;

	/** The posted on timestamp. */
	private Date postedOn;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the blog entry.
	 *
	 * @return the blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the blog entry.
	 *
	 * @param blogEntry
	 *            the new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets the users e-mail.
	 *
	 * @return the users e-mail
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the users e-mail.
	 *
	 * @param usersEMail
	 *            the new users e-mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Column(length = 4096, nullable = false)
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

	/**
	 * Gets the posted on timestamp.
	 *
	 * @return the posted on timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the posted on timestamp.
	 *
	 * @param postedOn
	 *            the new posted on timestamp
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}