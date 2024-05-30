package cybooks.controller;

import cybooks.model.Book;
import cybooks.model.Loan;
import cybooks.service.BookService;
import cybooks.service.LoanService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * The controller for searching for a book.
 */
public class SearchBookLoanController {
    BookService bookService = new BookService();
    LoanService loanService = new LoanService();
    boolean bookFound;
    String isbn;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox searchBookBox;

    @FXML
    private TextField searchInput;
    @FXML
    private Button searchBookButton;

    private VBox bookBox;

    public String getIsbn() {
        return isbn;
    }
    /**
     * Get the searchBookButton.
     * @return The searchBookButton.
     */
    public Button getSearchBookButton() {
        return searchBookButton;
    }
    /**
     * Initialize the controller.
     */
    public void initialize() {
        searchBookButton.setDisable(true);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBookButton.setDisable(newValue.trim().isEmpty());
        });
    }
    /**
     * Handle the search button action
     */
    @FXML
    private void handleSearchButton() {
        String searchText = searchInput.getText();
        String isbnSearch = searchText;
        searchInput.setText("");
        searchBookBox.getChildren().remove(bookBox);
        bookFound = false;
        try {
            if (!loanService.checkBookIsBorrowed(isbnSearch)) {
                throw new IllegalArgumentException("No book found with ISBN "+isbnSearch);
            }
            Book book = bookService.getBookByIsbn(isbnSearch);
            isbn = book.getIsbn();
            showBook(book);
            bookFound = true;
        } catch (IllegalArgumentException e) {
            isbn = null;
            System.out.println("Pas de livre avec l'ISBN "+isbnSearch+" emprunté !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Pas de livre avec l'ISBN "+isbnSearch+" emprunté !");
            alert.showAndWait();
        }
        System.out.println(bookFound);
    }
    /**
     * Show the book information
     * @param book The book to show
     */
    private void showBook(Book book) {
        // Create a VBox to hold all book information
        bookBox = new VBox();
        bookBox.setSpacing(10); // Set spacing between elements

        // Create and add a label for each piece of book information
        Label titleLabel = new Label("Titre : " + book.getTitle());
        titleLabel.setId("bookTitle"); // Set id for titleLabel
        bookBox.getChildren().add(titleLabel);

        Label authorLabel = new Label("Auteur(s) : " + book.getAuthor());
        authorLabel.setId("bookAuthor"); // Set id for authorLabel
        bookBox.getChildren().add(authorLabel);

        Label isbnLabel = new Label("ISBN : " + book.getIsbn());
        isbnLabel.setId("bookIsbn"); // Set id for isbnLabel
        bookBox.getChildren().add(isbnLabel);

        // Add the new book searchBookBox to the main searchBookBox
        searchBookBox.getChildren().add(bookBox);

        // Update the book label reference
    }


}