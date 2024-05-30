package cybooks.model;

import java.sql.Date;

/**
 * Class representing a user in the library system.
 */
public class User {
    private int id;
    private String lastName;
    private String firstName;
    private String mail;
    private String phoneNumber;
    private String adresse;
    private int postalCode;
    private int numberBooksLoan;
    private Date registrationDate;
    // Add other necessary attributes

    /**
     * Constructor for the User class.
     * @param id The user's ID.
     * @param lastName The user's last name.
     * @param firstName The user's first name.
     * @param phoneNumber The user's phone number
     * @param mail The user's email.
     * @param adresse The user's address.
     * @param postalCode The user's postal code.
     * @param registrationDate The user's registration date.
     * @param numberBooksLoan The number of books the user has loaned.
     */
    public User(int id, String firstName, String lastName, String phoneNumber, String mail, String adresse, int postalCode, Date registrationDate, int numberBooksLoan) {
        if(id != 0){
            this.id = id;
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.adresse = adresse;
        this.postalCode = postalCode;
        this.registrationDate = registrationDate;
        this.numberBooksLoan = numberBooksLoan;
    }


    /** ToString method
     * @return a string representation of the object.
     */
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", mail='" + mail + '\'' +
                ", adresse='" + adresse + '\'' +
                ", postalCode=" + postalCode +
                ", registrationDate=" + registrationDate +
                ", numberBooksLoan=" + numberBooksLoan +
                '}';
    }








    /**
     * Get the user's ID.
     * @return The user's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the user's ID.
     * @param id The user's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the user's last name.
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the user's last name.
     * @param lastName The user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the user's first name.
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user's first name.
     * @param firstName The user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;}
    /**
     * Get the user's phone number.
     * @return The user's phone number.
     */
    public String getMail() {
        return mail;
    }
    /**
     * Set the user's phone number.
     * @param mail The user's phone number.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    /**
     * Get the user's phone number.
     * @return The user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Set the user's phone number.
     * @param phoneNumber The user's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Get the user's address.
     * @return The user's address.
     */
    public String getAdresse() {
        return adresse;
    }
    /**
     * Set the user's address.
     * @param adresse The user's address.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    /**
     * Get the user's postal code.
     * @return The user's postal code.
     */
    public int getPostalCode() {
        return postalCode;
    }
    /**
     * Set the user's postal code.
     * @param postalCode The user's postal code.
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * Get the number of books the user has loaned.
     * @return The number of books the user has loaned.
     */
    public int getNumberBooksLoan() {
        return numberBooksLoan;
    }
    /**
     * Set the number of books the user has loaned.
     * @param numberBooksLoan The number of books the user has loaned.
     */
    public void setNumberBooksLoan(int numberBooksLoan) {
        this.numberBooksLoan = numberBooksLoan;
    }
    /**
     * Get the user's registration date.
     * @return The user's registration date.
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }
    /**
     * Set the user's registration date.
     * @param registrationDate The user's registration date.
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
