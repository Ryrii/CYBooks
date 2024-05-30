package cybooks.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    /**
     * Switch to the add user scene.
     * @param event The event.
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/addUser.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        // Obtenir la scène actuelle
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Changer la scène
        window.setScene(accueil2Scene);
        window.show();
    }
    /**
     * Switch to the edit user scene.
     * @param event The event.
     */
    @FXML
    private void handleButtonAction1(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/GererAdherant.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        // Obtenir la scène actuelle
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Changer la scène
        window.setScene(accueil2Scene);
        window.show();
    }
    /**
     * Switch to the add loan scene.
     * @param event The event.
     */
    @FXML
    private void switchSceneToEnregistrerEmprunt(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/addLoan.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        // Obtenir la scène actuelle
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Changer la scène
        window.setScene(accueil2Scene);
        window.show();
    }
    /**
     * Switch to the add loan scene.
     * @param event The event.
     */
    @FXML
    private void returnLoan(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/returnLoan.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        // Obtenir la scène actuelle
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Changer la scène
        window.setScene(accueil2Scene);
        window.show();
    }
    /**
     * Switch to the search user scene.
     * @param event The event.
     */
    @FXML
    private void searchBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/searchBook.fxml"));
        Parent editUserParent = loader.load();

        Scene editUserScene = new Scene(editUserParent);

        // Création d'un nouveau Stage
        Stage stage = new Stage();
        stage.setScene(editUserScene);
        stage.show();


    }
    /**
     * Switch to the show loan scene.
     * @param event The event.
     */
    @FXML
    private void showLoan(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/showLoan.fxml"));
        Parent editUserParent = loader.load();

        Scene editUserScene = new Scene(editUserParent);

        // Création d'un nouveau Stage
        Stage stage = new Stage();
        stage.setScene(editUserScene);
        stage.show();
    }
}