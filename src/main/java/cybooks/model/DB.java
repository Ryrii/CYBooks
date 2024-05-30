package cybooks.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with the database.
 */
public class DB {
    //private static final String USER
    private static final String URL = "jdbc:sqlite:db/cybooks.db";
    /**
     * Connect to the database.
     * @return A connection to the database.
     */
    public Connection connect() {
        Connection conn = null;
        try {
            //conn = DriverManager.getConnection(URL, USER, PASS);
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            e.printStackTrace();
        }
        return conn;
    }
    /**
     * Initialize the database.
     * @return A connection to the database.
     */
    public Connection initDatabase() {
        String url = "jdbc:sqlite:db/cybooks.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * Test the connection to the database.
     */
    public void testConnection() {
        Connection conn = connect();
        if (conn != null) {
            System.out.println("Connection test successful!");
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error while closing the connection");
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection test failed!");
        }
    }
    /**
     * Initialize the database.
     */
    public void initializeDatabase() {
        try (
                Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement()) {

            // Create tables if they don't exist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "firstname VARCHAR(255), " +
                    "lastname VARCHAR(255), " +
                    "phoneNumber VARCHAR(255), " +
                    "mail VARCHAR(255), " +
                    "adresse VARCHAR(255), " +
                    "postalCode INT, " +
                    "registrationDate DATE, " +
                    "numberBooksLoan INT" +
                    ")");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS loan (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_user INT, " +
                    "Isbn VARCHAR(255), " +
                    "loanDate DATE, " +
                    "dueDate DATE, " +
                    "returnDate DATE " +
                    ")");

        } catch (SQLException e) {
            System.out.println("Error while initializing the database");
            e.printStackTrace();
        }
    }
    /**
     * Get a user from the database.
     * @param userId The ID of the user.
     * @return The user with the given ID, or null if no such user exists.
     */
    public User getUser(int userId) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String phoneNumber = rs.getString("phoneNumber");
                String mail = rs.getString("mail");
                String adresse = rs.getString("adresse");
                int postalCode = rs.getInt("postalCode");
                Date registrationDate = rs.getDate("registrationDate");
                int numberBooksLoan = rs.getInt("numberBooksLoan");

                user = new User(userId, firstname, lastname, phoneNumber, mail, adresse, postalCode, registrationDate, numberBooksLoan);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving user");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Get all users from the database.
     * @return A list of all users.
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                String mail = rs.getString("mail");
                String adresse = rs.getString("adresse");
                int postalCode = rs.getInt("postalCode");
                Date registrationDate = rs.getDate("registrationDate");
                int numberBooksLoan = rs.getInt("numberBooksLoan");
                users.add(new User(id, firstName, lastName, phoneNumber, mail, adresse, postalCode, registrationDate, numberBooksLoan));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all users");
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Add a user to the database.
     * @param user The user to add.
     */
    public void addUser(User user) {
        String query = "INSERT INTO user ( firstname, lastname, phoneNumber, mail, adresse, postalCode, registrationDate, numberBooksLoan) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getMail());
            stmt.setString(5, user.getAdresse());
            stmt.setInt(6, user.getPostalCode());
            stmt.setDate(7, user.getRegistrationDate());
            stmt.setInt(8, user.getNumberBooksLoan());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user");
            e.printStackTrace();

        }
    }
    /**
     * Update a user in the database.
     * @param user The user to update.
     */
    public void updateUser(User user) {
        String query = "UPDATE user SET firstname = ?, lastname = ?, phoneNumber = ?, mail = ?, adresse = ?, postalCode = ?, registrationDate = ?, numberBooksLoan = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getMail());
            stmt.setString(5, user.getAdresse());
            stmt.setInt(6, user.getPostalCode());
            stmt.setDate(7, user.getRegistrationDate());
            stmt.setInt(8, user.getNumberBooksLoan());
            stmt.setInt(9, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating user");
            e.printStackTrace();
        }
    }
    /**
     * Delete a user from the database.
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(int userId) {
        String query = "DELETE FROM user WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting user");
            e.printStackTrace();
        }
    }
    /**
     * Search for users in the database.
     * @param firstName The first name of the user to search for.
     * @param lastName The last name of the user to search for.
     * @return A list of users matching the search criteria.
     */
    public List<User> searchUsers(String firstName, String lastName) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE firstname = ? AND lastname = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String numberPhone = rs.getString("phoneNumber");
                String mail = rs.getString("mail");
                String adresse = rs.getString("adresse");
                int postalCode = rs.getInt("postalCode");
                Date registaionDate = rs.getDate("registrationDate");
                int numberBooksLoan = rs.getInt("numberBooksLoan");
                User user = new User(id, firstname, lastname, numberPhone, mail, adresse, postalCode, registaionDate, numberBooksLoan);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching users");
            e.printStackTrace();
        }
        return users;
    }


    /**
     * Get the number of loans a user currently has.
     * @param userId The ID of the user.
     * @return The number of loans the user currently has.
     */
    public int getUserLoanCount(int userId) {
        int loanCount = 0;

        String query = "SELECT COUNT(*) FROM loan WHERE id_user = ? AND returnDate IS NULL";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loanCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user loan count");
            e.printStackTrace();
        }

        return loanCount;
    }

    /**
     * Get the number of times a book has been loaned out.
     * @param bookIsbn The ISBN of the book.
     * @return The number of times the book has been loaned out.
     */
    public int getBookLoanCount(String bookIsbn) {
        int loanCount = 0;

        String query = "SELECT COUNT(*) FROM historyLoan WHERE Isbn = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookIsbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loanCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting book loan count");
            e.printStackTrace();
        }

        return loanCount;
    }


    /**
     * Check if a book is currently on loan.
     * @param bookIsbn The ISBN of the book.
     * @return True if all copies of the book are currently on loan, false otherwise.
     */
    public boolean isBookOnLoan(String bookIsbn) {
        boolean isOnLoan = false;

        String query = "SELECT COUNT(*) FROM loan WHERE Isbn = ? AND returnDate IS NULL";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookIsbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int loanCount = rs.getInt(1);
                if (loanCount >= 1) { // All copies of the book are on loan
                    isOnLoan = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if book is on loan");
            e.printStackTrace();
        }

        return isOnLoan;
    }

    /**
     * Add a loan to the database.
     * @param loan The loan to add.
     */
    public void addLoan(Loan loan) {
    System.out.println(loan);

        String query = "INSERT INTO loan (id_user, Isbn, loanDate, dueDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, loan.getUserId());
            stmt.setString(2, loan.getBookIsbn());
            stmt.setDate(3, loan.getLoanDate());
            stmt.setDate(4, loan.getDueDate()); // Use the calculated due date
            stmt.executeUpdate();
            // If the loan was successfully added, increment the number of books the user has loaned
            String updateUserQuery = "UPDATE user SET numberBooksLoan = numberBooksLoan + 1 WHERE id = ?";
            try (PreparedStatement updateUserStmt = conn.prepareStatement(updateUserQuery)) {
                updateUserStmt.setInt(1, loan.getUserId());
                updateUserStmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error while updating user's number of books loaned");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error while adding loan");
            e.printStackTrace();
        }
    }

    /**
     * Return a loan to the library.
     * @param userId The ID of the user returning the book.
     * @param bookIsbn The ISBN of the book being returned.
     */
    public void returnLoan(int userId, String bookIsbn) {
        // Check if the loan exists in the currentLoan table
        String checkQuery = "SELECT * FROM loan WHERE id_user = ? AND Isbn = ? AND returnDate IS NULL";
        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, userId);
            checkStmt.setString(2, bookIsbn);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No loan found with the given user ID and book ISBN.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if loan exists");
            e.printStackTrace();
            return;
        }


        // Fetch the loan from the currentLoan table
        String fetchQuery = "SELECT * FROM loan WHERE id_user = ? AND Isbn = ? AND returnDate IS NULL";
        try (Connection conn = connect();
             PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery)) {
                fetchStmt.setInt(1, userId);
                fetchStmt.setString(2, bookIsbn);
                ResultSet rs = fetchStmt.executeQuery();
                if (rs.next()) {

                    // update the return date
                    String deleteQuery = "UPDATE loan SET returnDate = ? WHERE id_user = ? AND Isbn = ? AND returnDate IS NULL";
                    try (PreparedStatement updateStmt = conn.prepareStatement(deleteQuery)) {
                        updateStmt.setDate(1, new Date(System.currentTimeMillis()));
                        updateStmt.setInt(2, userId);
                        updateStmt.setString(3, bookIsbn);
                        updateStmt.executeUpdate();
                    }


                // If the loan was successfully returned, decrement the number of books the user has loaned
                String updateUserQuery = "UPDATE user SET numberBooksLoan = numberBooksLoan - 1 WHERE id = ?";
                try (PreparedStatement updateUserStmt = conn.prepareStatement(updateUserQuery)) {
                    updateUserStmt.setInt(1, userId);
                    updateUserStmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error while updating user's number of books loaned");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while returning loan");
            e.printStackTrace();
        }
    }



    /**
     * Get a loan from the database.
     * @param userId The ID of the loan.
     * @return The loan with the given ID, or null if no such loan exists.
     */
    public List<Loan> getUserCurrentLoans(int userId) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE returnDate IS NULL AND id_user = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String bookIsbn = rs.getString("Isbn");
                Date loanDate = rs.getDate("loanDate");
                Date dueDate = rs.getDate("dueDate");
                loans.add(new Loan(id,userId, bookIsbn, loanDate, dueDate, null));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting current loans");
            e.printStackTrace();
        }
        return loans;
    }

    /**
     * Get a loan from the database.
     * @param userId The ID of the loan.
     * @return The loan with the given ID, or null if no such loan exists.
     */
    public List<Loan> getUserHistoryLoans(int userId) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE id_user = ? AND returnDate IS NOT NULL";
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String bookIsbn = rs.getString("Isbn");
                Date loanDate = rs.getDate("loanDate");
                Date dueDate = rs.getDate("dueDate");
                Date returnDate = rs.getDate("returnDate");
                loans.add(new Loan(id,userId, bookIsbn, loanDate, dueDate, returnDate));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting loan history");
            e.printStackTrace();
        }
        return loans;
    }


    /**
     * Get all delayed loans from the database.
     * @return A list of all delayed loans.
     */
    public ArrayList<Loan> getDelayedLoans() {
        ArrayList<Loan> delayedLoans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE dueDate < ? AND returnDate IS NULL";

        try (Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new Date(System.currentTimeMillis()-1000*60*60*24));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                int userId = rs.getInt("id_user");
                String bookIsbn = rs.getString("Isbn");
                Date loanDate = rs.getDate("loanDate");
                Date dueDate = rs.getDate("dueDate");
                Loan loan = new Loan(id, userId, bookIsbn, loanDate, dueDate, null);
                delayedLoans.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting delayed loans");
            e.printStackTrace();
        }

        return delayedLoans;
    }



    /**
     * Get all current loans from the database.
     * @return A list of all current loans.
     */
    public ArrayList<Loan> getCurrentLoans() {
        ArrayList<Loan> currentLoans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE returnDate IS NULL";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("id_user");
                String bookIsbn = rs.getString("Isbn");
                Date loanDate = rs.getDate("loanDate");
                Date dueDate = rs.getDate("dueDate");
                currentLoans.add(new Loan(id,userId, bookIsbn, loanDate, dueDate, null));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting current loans");
            e.printStackTrace();
        }

        return currentLoans;
    }


    /**
     * Get all loan history from the database.
     * @return A list of all loan history.
     */
    public ArrayList<Loan> getLoanHistory() {
        ArrayList<Loan> loanHistory = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE returnDate IS NOT NULL";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                int userId = rs.getInt("id_user");
                String bookIsbn = rs.getString("Isbn");
                Date loanDate = rs.getDate("loanDate");
                Date dueDate = rs.getDate("dueDate");
                Date returnDate = rs.getDate("returnDate");

                loanHistory.add(new Loan(id, userId, bookIsbn, loanDate, dueDate, returnDate));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting loan history");
            e.printStackTrace();
        }

        return loanHistory;
    }

    /**
     * Get the most loaned books in the last 30 days from the database.
     * @return A list of the most borrowed books in the last 30 days.
     */
    public ArrayList<String> getMostLoanedBooks() {
        ArrayList<String> mostLoanedBooks = new ArrayList<>();
        String query = "SELECT Isbn, COUNT(*) as count FROM (" +
                "SELECT Isbn FROM currentLoan WHERE loanDate >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                "UNION ALL " +
                "SELECT Isbn FROM loanHistory WHERE loanDate >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)" +
                ") AS loans GROUP BY Isbn ORDER BY count DESC";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String bookIsbn = rs.getString("Isbn");
                mostLoanedBooks.add(bookIsbn);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting the most loaned books");
            e.printStackTrace();
        }

        return mostLoanedBooks;
    }
/**
 * Get the most loaned books in the last 30 days from the database.
 * @return A list of the most borrowed books in the last 30 days.
 */
public boolean checkBookIsBorrowed(String isbn){
    boolean isBorrowed = false;
    String query = "SELECT * FROM loan WHERE Isbn = ? AND returnDate IS NULL";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, isbn);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            isBorrowed = true;
        }
    } catch (SQLException e) {
        System.out.println("Error while checking if book is borrowed");
        e.printStackTrace();
    }
    return isBorrowed;
}


}



