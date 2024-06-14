package hr.fer.android.hw0036493805.hw17.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * This class represents a model for the user profile which is built from json.
 *
 * @author Karlo Bencic
 */
public class UserProfile implements Serializable {

    /**
     * The avatar url.
     */
    @SerializedName("avatar_location")
    private String avatarUrl;

    /**
     * The first name.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The last name.
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The phone number.
     */
    @SerializedName("phone_no")
    private String phoneNumber;

    /**
     * The email.
     */
    @SerializedName("email_sknf")
    private String email;

    /**
     * The spouse.
     */
    @SerializedName("spouse")
    private String spouse;

    /**
     * The age.
     */
    @SerializedName("age")
    private String age;

    /**
     * Gets the age.
     *
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param age the new age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Gets the avatar url.
     *
     * @return the avatar url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Sets the avatar url.
     *
     * @param avatarUrl the new avatar url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the spouse.
     *
     * @return the spouse
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Sets the spouse.
     *
     * @param spouse the new spouse
     */
    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
