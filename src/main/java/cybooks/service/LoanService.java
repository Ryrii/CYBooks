package cybooks.service;

import java.sql.Date;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import cybooks.model.Loan;
import cybooks.model.User;
import cybooks.model.Book;
import cybooks.model.DB;
/**
 * Service class for managing loans in the library system.
 */
public class LoanService {

    /**
     * Constructor for the LoanService class.
     */
    public LoanService() {

    }

    /**
     * Add a loan.
     * @param userId The user who borrowed the book.
     * @param bookIsbn The book that was borrowed.
     */
    public void addLoan(int userId, String bookIsbn) {
        DB db = new DB();
        if (db.isBookOnLoan(bookIsbn)) {
            throw new IllegalArgumentException("The book is on loan.");
        }
        Date loanDate = new Date(System.currentTimeMillis());
        Date dueDate = new Date(System.currentTimeMillis()+1000*60*60*24*15);
        Loan loan = new Loan(null, userId, bookIsbn, loanDate, dueDate, null);
        db.addLoan(loan);
    }
    /**
     * Get the number of loans for a given user.
     * @param userId The user whose loans to count.
     * @return The number of loans for the given user.
     */
    public int getUserLoanCount (int userId) {
        DB db = new DB();
        return db.getUserLoanCount(userId);
    }

    /**
     * Get a loan by its ID.
     * @param id The loan's ID.
     * @return The loan with the given ID, or null if no such loan exists.
     */
    public Loan getLoan(int id) {

        return null;
    }

    /**
     * Return a loan.
     * @param userId The user who borrowed the book.
     * @param bookIsbn The book that was borrowed.
     */
    public void returnLoan(int userId,String bookIsbn) {
        DB db = new DB();
        db.returnLoan(userId, bookIsbn);
    }

    /**
     * Get all loans for a given user.
     * @param user The user whose loans to retrieve.
     * @return A list of all loans for the given user.
     */
    public ArrayList<Loan> getLoansForUser(User user) {

        return null;
    }

    /**
     * Get all loans for a given book id.
     * @param bookIsbn The book id whose loans to retrieve.
     * The book id is the id of the book that was borrowed.
     */
    public ArrayList<Loan> getLoansForBook(String bookIsbn) {

        return null;
    }

    /**
     * Get all current loans in the library system.
     * @return A list of all current loans in the library system.
     */
    public ArrayList<Loan> getCurrentLoans() {
        DB db = new DB();
        return db.getCurrentLoans();
    }

    /**
     * Get current loans for a given user.
     * @param userId The user whose loans to retrieve.
     * @return A list of all loans for the given user.
     */
    public List<Loan> getUserCurrentLoans(int userId) {
        DB db = new DB();
        return db.getUserCurrentLoans(userId);
    }


    /**
     * Get all loan history in the library system.
     * @return A list of all loan history in the library system.
     */
    public ArrayList<Loan> getLoanHistory() {
        DB db = new DB();
        return db.getLoanHistory();
    }

    /**
     * Get current loans for a given user.
     * @param userId The user whose loans to retrieve.
     * @return A list of all loans for the given user.
     */
    public List<Loan> getUserHistoryLoans(int userId) {
        DB db = new DB();
        List<Loan> loans = db.getUserHistoryLoans(userId);
        return loans;
    }


    /**
     * Get all delayed loans in the library system.
     * @return A list of all delayed loans in the library system.
     */
    public ArrayList<Loan> getDelayedLoans() {
        DB db = new DB();
        return db.getDelayedLoans();
    }
    /**
     * Check if a book is borrowed.
     * @param bookIsbn The book to check.
     * @return True if the book is borrowed, false otherwise.
     */
    public boolean checkBookIsBorrowed(String bookIsbn) {
        DB db = new DB();
        return db.checkBookIsBorrowed(bookIsbn);
    }


}

