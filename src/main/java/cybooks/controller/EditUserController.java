package cybooks.controller;

import cybooks.model.User;
import cybooks.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditUserController {

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
    private User currentUser;
    /**
     * Initialize the controller.
     */
    public void initialize() {
        userService = new UserService();
    }
    /**
     * Initialize the data.
     * @param user The user.
     */
    public void initData(User user) {
        currentUser = user;
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        phoneNumberField.setText(user.getPhoneNumber());
        mailField.setText(user.getMail());
        adresseField.setText(user.getAdresse());
        postalCodeField.setText(String.valueOf(user.getPostalCode()));
    }
    /**
     * Update a user.
     */
    @FXML
    private void updateUser() {
    System.out.println("updateUser");
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String mail = mailField.getText();
        String adresse = adresseField.getText();
        String postalCodeText = postalCodeField.getText();

        if(!validateFields(firstName, lastName, phoneNumber, mail, adresse, postalCodeText)){
            return;
        }
        userService.updateUser(currentUser.getId(), firstName, lastName, phoneNumber, mail, adresse, Integer.parseInt(postalCodeText));
        Stage stage = (Stage) firstNameField.getScene().getWindow();

        //alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Adhérant modifié avec succès !");
        alert.showAndWait();

        stage.close();
    }
    /**
     * Validate the fields.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param phoneNumber The phone number.
     * @param mail The mail.
     * @param adresse The address.
     * @param postalCodeText The postal code.
     * @return True if the fields are valid, false otherwise.
     */
    private boolean validateFields(String firstName, String lastName, String phoneNumber, String mail, String adresse, String postalCodeText) {
        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || mail.isEmpty() || adresse.isEmpty() || postalCodeText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return false;
        }
        int postalCode;
        try {
            postalCode = Integer.parseInt(postalCodeText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le code postal doit être un nombre.");
            alert.showAndWait();
            return false;
        }
        return true;
    }


}