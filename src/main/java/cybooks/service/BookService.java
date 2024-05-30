package cybooks.service;

import java.util.ArrayList;

import cybooks.model.DB;
import cybooks.service.BnfApiSparqlService;
import cybooks.model.Book;
import org.json.JSONObject;

/**
 * Service class for managing books in the library system.
 */
public class BookService {
    /**
     * The BNF API service used to get book information.
     */
    private BnfApiSparqlService bnfApi;
    /**
     * Constructor for the BookService class.
     */
    public BookService() {
        bnfApi = new BnfApiSparqlService();
    }
    /**
     * Get a book by its ISBN.
     * @param isbn The ISBN of the book to get.
     * @return The book with the given ISBN.
     */
    public Book getBookByIsbn(String isbn) {
        JSONObject bookInfo = bnfApi.getBookWithISBN(isbn);
        try {
            String title = bookInfo.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("title").getString("value");
            String author = bookInfo.getJSONObject("results").getJSONArray("bindings").getJSONObject(0).getJSONObject("author").getString("value");
            return new Book(isbn, title, author);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting book with ISBN " + isbn, e);

        }
    }
    /**
     * Check if a book with a given ISBN exists.
     * @param isbn The ISBN of the book to check.
     * @return True if a book with the given ISBN exists, false otherwise.
     */
    public boolean checkBookExists(String isbn) {
        return bnfApi.checkIsbnExists(isbn);
    }


    /**
     * Get the most borrowed books in the last 30 days.
     * @return A list of the most borrowed books in the last 30 days.
     */
    public ArrayList<String> getMostLoanedBooks() {
        DB db = new DB();
        return db.getMostLoanedBooks();
    }


}
