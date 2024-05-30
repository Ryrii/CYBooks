package cybooks.service;

import cybooks.model.User;
import java.util.ArrayList;
import cybooks.model.DB;
import java.util.List;
import java.sql.Date;

/**
 * Service class for managing users in the library system.
 */
public class UserService {
    /**
     * Constructor for the UserService class.
     */
    public UserService() {

    }

   /**
     * Add a user.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param phoneNumber The user's phone number
     * @param mail The user's email.
     * @param adresse The user's address.
     * @param postalCode The user's postal code.
     */
    public void addUser(String firstName, String lastName, String phoneNumber, String mail, String adresse, int postalCode) {
        User user = new User(0,firstName, lastName, phoneNumber, mail, adresse, postalCode, new Date(System.currentTimeMillis()), 0);

        DB db = new DB();
        db.addUser(user);
    }

    /**
     * Get a user by their ID.
     * @param id The user's ID.
     * @return The user with the given ID, or null if no such user exists.
     */
    public User getUser(int id) {
        DB db = new DB();
        User user = db.getUser(id);
        return user;
    }

    /**
     * Update a user's information.
        * @param id The ID of the user to update.
        * @param firstName The user's first name.
        * @param lastName The user's last name.
        * @param phoneNumber The user's phone number
        * @param mail The user's email.
        * @param adresse The user's address.
        * @param postalCode The user's postal code.
        */
    public void updateUser(int id, String firstName, String lastName, String phoneNumber, String mail, String adresse, int postalCode) {
        System.out.println("Updating user with ID: " + id);
        DB db = new DB();
        User existingUser = db.getUser(id);
        System.out.println(existingUser);
        if (existingUser == null) {
            System.out.println("No user found with the given ID. Please enter a valid user ID.");
            return;
        }
        System.out.println(existingUser.getRegistrationDate() + " " + existingUser.getNumberBooksLoan());
        User user = new User(id, firstName, lastName, phoneNumber, mail, adresse, postalCode, existingUser.getRegistrationDate(), existingUser.getNumberBooksLoan());
        db.updateUser(user);
    }
    /**
     * Delete a user from the library system.
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(int userId) {
        DB db = new DB();
        db.deleteUser(userId);
    }
    /**
     * Search for users by first name and last name.
     * @param firstName The first name of the user to search for.
     * @param lastName The last name of the user to search for.
     * @return A list of users with the given first and last names.
     */
    public List<User> searchUsers(String firstName, String lastName) {
        DB db = new DB();
        List<User> users = db.searchUsers(firstName, lastName);
        if (users.isEmpty()) {
            System.out.println("No users found with the name: " + firstName + " " + lastName);
        }
        return users;
    }

    /**
     * Get all users in the library system.
     * @return A list of all users in the library system.
     */
    public ArrayList<User> getAllUsers() {
        DB db = new DB();
        return db.getAllUsers();
    }
}
