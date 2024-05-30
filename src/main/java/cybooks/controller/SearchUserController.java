package cybooks.controller;

import cybooks.model.User;
import cybooks.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * The controller for searching for a user.
 */
public class SearchUserController {
    public HBox searchUserHBox;
    UserService userService = new UserService() ;
    @FXML
    private VBox searchUserBox;

    @FXML
    private TextField searchUserInput;
    @FXML
    private Button searchUserButton;

    private int userId;

    private Label userLabel; // Ajout d'un champ pour stocker le Label de l'utilisateur actuellement affiché

    public int getUserId() {
        return userId;
    }
    public Button getSearchUserButton() {
        return searchUserButton;
    }public Label getUserLabel() {
        return userLabel;
    }

    public void initialize() {
        searchUserInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                searchUserInput.setText(oldValue);
            }
        });
        searchUserButton.setDisable(true);
        searchUserInput.textProperty().addListener((observable, oldValue, newValue) -> {
            searchUserButton.setDisable(newValue.trim().isEmpty());
        });
    }
    
    /**
     * Handle the search button action
     */
    @FXML
    private void handleSearchButton() {
        String searchText = searchUserInput.getText();
        int id = Integer.parseInt(searchText);
        searchUserBox.getChildren().remove(userLabel);
        searchUserInput.setText("");
        try {
            User user = userService.getUser(id);
            if (user == null) {
                throw new IllegalArgumentException("Pas d'utilisateur avec l'id "+id+" trouvé !");
            }
            userId = user.getId();
            showUser(user);
        } catch (IllegalArgumentException e) {
            userId = 0;
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Pas d'utilisateur avec l'id "+id+" trouvé !");
            alert.showAndWait();
        }
    }
    /**
     * Show the user.
     * @param user The user.
     */
    private void showUser(User user) {
        userLabel = new Label(userId+" "+ user.getFirstName() + " " + user.getLastName());
        searchUserBox.getChildren().add(userLabel);

    }
}