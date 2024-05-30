package cybooks.controller;

import cybooks.service.LoanService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for adding a loan.
 */
public class ReturnLoanController {
    LoanService loanService = new LoanService();
    private int userId;
    private String isbn;
    /**
     * The VBox for adding a loan.
     */
    @FXML
    private VBox addLoanBox;
    /**
     * The user field for adding a loan.
     */
    @FXML
    private TextField userField;
    /**
     * The book field for adding a loan.
     */
    @FXML
    private TextField bookField;
    /**
     * The add button for adding a loan.
     */
    @FXML
    private Button addButton;
    /**
     * The search user button for adding a loan.
     */
    @FXML
    private Button searchUserButton;
    /**
     * The search book button for adding a loan.
     */
    @FXML
    private Button searchBookButton;
    @FXML
    private Button searchUserHBox;
    /**
     * The search user controller for adding a loan.
     */
    private SearchUserController searchUserController;
    /**
     * The search book controller for adding a loan.
     */
    private SearchBookLoanController searchBookLoanController;
    /**
     * Initializes the add loan controller.
     */
    @FXML
    private void initialize() throws IOException {
       //addLoanBox.getChildren().clear();

        addSearchUser();
        addSearchBook();

        //addLoanBox.getChildren().add(addButton);
        addButton.setDisable(true);
        addButton.setOnAction(event -> handleAddButton());
    }
    /**
     * Adds a search user to the database.
     */
    private void addSearchUser() throws IOException {
        FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/Fxml/searchUser.fxml"));
        VBox searchUser = userLoader.load();
        searchUserController = userLoader.getController();
        Button searchUserButton = searchUserController.getSearchUserButton();
        searchUserButton.addEventHandler(ActionEvent.ACTION, event -> {
            Platform.runLater(() -> {
                checkValidate();
            });
        });
        addLoanBox.getChildren().add(searchUser);
    }
    /**
     * Adds a search book to the database.
     */
    private void addSearchBook() throws IOException {

        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("/Fxml/searchBookLoan.fxml"));
        VBox searchUser = bookLoader.load();
        searchBookLoanController = bookLoader.getController();
        Button searchUserButton = searchBookLoanController.getSearchBookButton();
        searchUserButton.addEventHandler(ActionEvent.ACTION, event -> {
            Platform.runLater(() -> {
                checkValidate();
            });
        });
        addLoanBox.getChildren().add(searchUser);
    }
    /**
     * Return a loan to the database.
     */
    @FXML
    private void handleAddButton() {
        try {
            loanService.returnLoan(userId, isbn);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Le livre a été retourné avec succès !");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Le livre avec l'isbn " + isbn + " n'a pas été emprunté par l'utilisateur avec l'id " + userId + " !");
            alert.showAndWait();


        }
    }
    /**
     * Checks if the user and book fields are filled in.
     */
    private void checkValidate() {
        userId = searchUserController.getUserId();
        isbn = searchBookLoanController.getIsbn();
        if (userId != 0 && isbn != null) {
            addButton.setDisable(false);
        }else{
            addButton.setDisable(true);

        }
    }

    /**
     * Switches the scene to the home page.
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchSceneToHome(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/home.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(accueil2Scene);
        window.show();
    }
}