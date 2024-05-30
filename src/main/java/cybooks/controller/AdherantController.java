package cybooks.controller;

import cybooks.model.User;
import cybooks.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Date;
/**
 * The controller for managing users.
 */
public class AdherantController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField postalCodeField;

    private UserService userService;
    /**
     * Initialize the controller.
     */
    public AdherantController() {
        userService = new UserService();
    }
    /**
     * Add a user.
     * @param event The event.
     */
    @FXML
    public void addUser(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String mail = mailField.getText();
        String adresse = adresseField.getText();
        String postalCodeText = postalCodeField.getText();

        // Vérification des champs
        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || mail.isEmpty() || adresse.isEmpty() || postalCodeText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

        // Vérification du code postal
        int postalCode;
        try {
            postalCode = Integer.parseInt(postalCodeText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le code postal doit être un nombre.");
            alert.showAndWait();
            return;
        }
        userService.addUser(firstName, lastName, phoneNumber, mail, adresse, postalCode);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'utilisateur a été ajouté avec succès.");
        alert.showAndWait();

        // Vider les champs
        firstNameField.setText("");
        lastNameField.setText("");
        phoneNumberField.setText("");
        mailField.setText("");
        adresseField.setText("");
        postalCodeField.setText("");

    }
    /**
     * Switch to the home scene.
     * @param event The event.
     * @throws IOException If the FXML file is not found.
     */
    @FXML
    private void switchSceneToAccueil(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/home.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        // Obtenir la scène actuelle
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Changer la scène
        window.setScene(accueil2Scene);
        window.show();
    }
}
