package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The {@code BlogUser} class models the blog user.
 * 
 * @author Karlo Bencic
 * 
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/** The unique user id. */
	private Long id;

	/** The blog entries that user created. */
	private List<BlogEntry> createdEntries = new ArrayList<>();

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The unique nickname. */
	private String nick;

	/** The user email. */
	private String email;

	/** The password hash. */
	private String passwordHash;

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
	 * Sets the unique id.
	 *
	 * @param id
	 *            the new unique id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	@Column(nullable = false, length = 25)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	@Column(nullable = false, length = 25)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	@Column(nullable = false, unique = true, length = 25)
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nickname.
	 *
	 * @param nick
	 *            the new nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	@Column(nullable = false, unique = true, length = 25)
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	@Column(nullable = false, length = 40)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash
	 *            the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the created entries.
	 *
	 * @return the created entries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getCreatedEntries() {
		return createdEntries;
	}

	/**
	 * Sets the created entries.
	 *
	 * @param createdEntries
	 *            the new created entries
	 */
	public void setCreatedEntries(List<BlogEntry> createdEntries) {
		this.createdEntries = createdEntries;
	}
}