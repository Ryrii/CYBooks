package cybooks.model;

import java.sql.Date;
import java.util.Objects;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 * Class representing a loan (book borrowing) in the library system.
 */
public class Loan {
    private Integer id;
    private int userId;
    private String bookIsbn;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private int delay;

    /**
     * Constructor for the Loan class.
     * @param id The loan's ID.
     * @param userId The ID of the user who borrowed the book.
     * @param bookIsbn The ID of the book that was borrowed.
     * @param loanDate The date the book was borrowed.
     * @param dueDate The date the book is due to be returned.
     * @param returnDate The date the book was returned.
     */
    public Loan(Integer id, int userId, String bookIsbn, Date loanDate, Date dueDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        if (returnDate == null) {
            this.delay = 0;
        }else {
            this.delay = (returnDate.toLocalDate().isAfter(dueDate.toLocalDate())) ? Math.abs((int) ChronoUnit.DAYS.between(dueDate.toLocalDate(), returnDate.toLocalDate())) : 0;
            }
    }




    /**
     * Get the loan's ID.
     * @return The loan's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the loan's ID.
     * @param id The loan's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the ID of the user who borrowed the book.
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the ID of the user who borrowed the book.
     * @param userId The user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the ID of the book that was borrowed.
     * @return The book ID.
     */
    public String getBookIsbn() {
        return bookIsbn;
    }

    /**
     * Set the ID of the book that was borrowed.
     * @param bookId The book ID.
     */
    public void setBookIsbn(int bookId) {
        this.bookIsbn = bookIsbn;
    }

    /**
     * Get the date the book was borrowed.
     * @return The loan date.
     */
    public Date getLoanDate() {
        return loanDate;
    }
    /**
     * Get the date the book is due to be returned.
     * @return The return the due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the date the book was borrowed.
     * @param loanDate The loan date.
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Get the date the book is due to be returned.
     * @return The return date.
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Set the date the book is due to be returned.
     * @param returnDate The return date.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    /**
     * Get the number of days the book was returned after the due date.
     * @return The number of days the book was returned after the due date.
     */
    public int getDelay() {
        return delay;
    }
    /**
     * Set the number of days the book was returned after the due date.
     * @param delay The number of days the book was returned after the due date.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
    /**
     * Override the equals method to compare two Loan objects.
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id &&
                userId == loan.userId &&
                bookIsbn == loan.bookIsbn &&
                Objects.equals(loanDate, loan.loanDate) &&
                Objects.equals(returnDate, loan.returnDate);
    }

    /**
     * Override the hashCode method.
     * @return The hash code of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, bookIsbn, loanDate, returnDate);
    }

    /**
     * Override the toString method to provide a string representation of the Loan object.
     * @return A string representation of the Loan object.
     */
    @Override
    public String toString() {
        String str =  "Loan{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookIsbn +
                ", loanDate=" + loanDate+
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", delay=" + delay +
                '}';

        return  str;
    }
}
