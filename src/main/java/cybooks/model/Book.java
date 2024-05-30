package cybooks.model;

import java.util.Objects;

/**
 * Class representing a book in the library system.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    // Add other necessary attributes

    /**
     * Constructor for the Book class.
     * @param isbn The book's ID.
     * @param title The book's title.
     * @param author The book's author.
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    /**
     * Get the book's ID.
     * @return The book's ID.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Set the book's ID.
     * @param isbn The book's ID.
     */
    public void setId(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the book's title.
     * @return The book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the book's title.
     * @param title The book's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the book's author.
     * @return The book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the book's author.
     * @param author The book's author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    // Add other necessary methods
    // ...

    /**
     * Override the equals method to compare two Book objects.
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }

    /**
     * Override the hashCode method.
     * @return The hash code of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }

    /**
     * Override the toString method to provide a string representation of the Book object.
     * @return A string representation of the Book object.
     */
    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
