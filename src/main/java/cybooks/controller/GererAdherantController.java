package cybooks.controller;

import cybooks.model.User;
import cybooks.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GererAdherantController implements Initializable {

    @FXML
    private TableView<User> adherantTableView;

    @FXML
    private TableColumn<User, Integer> idAdherantColumn;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TextField keywordTextField;
    @FXML
    private Label idLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label codePostalLabel;
    @FXML
    private Label adresseLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label mailLabel;
    @FXML
    private Label registrationDate;
    @FXML
    private Label numberBooksLoan;


    private UserService userService = new UserService();
    /**
     * Initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {


        List<User> users = userService.getAllUsers();

        ObservableList<User> userModelObservableList = FXCollections.observableArrayList(users);

        idAdherantColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        adherantTableView.setItems(userModelObservableList);

        FilteredList<User> filteredData = new FilteredList<>(userModelObservableList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(User -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (User.getLastName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getFirstName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(User.getId()).contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(adherantTableView.comparatorProperty());
        adherantTableView.setItems(sortedData);

        adherantTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    /**
     * Show the person details.
     * @param user The user.
     */
    private void showPersonDetails(User user) {
        if (user != null) {
            idLabel.setText(Integer.toString(user.getId()));
            prenomLabel.setText(user.getFirstName());
            nomLabel.setText(user.getLastName());
            codePostalLabel.setText(Integer.toString(user.getPostalCode()));
            adresseLabel.setText(user.getAdresse());
            mailLabel.setText(user.getMail());
            telephoneLabel.setText(user.getPhoneNumber());
            registrationDate.setText(user.getRegistrationDate().toString());
            numberBooksLoan.setText(Integer.toString(user.getNumberBooksLoan()));
        } else {
            System.out.println("Aucun adhérent sélectionné");
            idLabel.setText("");
            prenomLabel.setText("");
            nomLabel.setText("");
            codePostalLabel.setText("");
            adresseLabel.setText("");
            mailLabel.setText("");
            telephoneLabel.setText("");
            registrationDate.setText("");
            numberBooksLoan.setText("");
        }
    }
    /**
     * Switch the scene to the home page.
     * @param event The event.
     */
    @FXML
    private void switchSceneToHome(ActionEvent event) throws IOException {
        Parent accueil2Parent = FXMLLoader.load(getClass().getResource("/Fxml/home.fxml"));
        Scene accueil2Scene = new Scene(accueil2Parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(accueil2Scene);
        window.show();
    }
    /**
     * Switch the scene to the add user page.
     * @param event The event.
     */
    @FXML
    private void editUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/editUser.fxml"));
        Parent editUserParent = loader.load();

        Scene editUserScene = new Scene(editUserParent);

        // Création d'un nouveau Stage
        Stage stage = new Stage();
        stage.setScene(editUserScene);

        // Initialisation du contrôleur
        EditUserController controller = loader.getController();
        if (adherantTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun adhérent sélectionné");
            alert.setContentText("Veuillez sélectionner un adhérent à modifier");
            alert.showAndWait();

            return;
        }
        controller.initData(adherantTableView.getSelectionModel().getSelectedItem());

        // Affichage du nouveau Stage
        stage.show();

        // attendre que le stage soit fermé
        stage.setOnHidden(e -> {
            adherantTableView.setItems(FXCollections.observableArrayList());
            adherantTableView.getItems().addAll(userService.getAllUsers());
        });
    }
    /**
     * Add a user.
     */
    @FXML
    private void deleteUser() {
        User selectedUser = adherantTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun adhérent sélectionné");
            alert.setContentText("Veuillez sélectionner un adhérent à supprimer");
            alert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Suppression d'un adhérent");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'adhérent avec l'ID : " + selectedUser.getId() + " ?");

        // Attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int userId = selectedUser.getId();
                userService.deleteUser(userId);
                adherantTableView.setItems(FXCollections.observableArrayList());
                adherantTableView.getItems().addAll(userService.getAllUsers());
            }
        });
    }
    /**
     * Add a user.
     */
    @FXML
    private void userLoanHistory() throws IOException {
        User selectedUser = adherantTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun adhérent sélectionné");
            alert.setContentText("Veuillez sélectionner un adhérent pour voir son historique d'emprunts");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/userLoanHistory.fxml"));
        Parent userLoanHistoryParent = loader.load();

        Scene userLoanHistoryScene = new Scene(userLoanHistoryParent);

        // Création d'un nouveau Stage
        Stage stage = new Stage();
        stage.setScene(userLoanHistoryScene);

        // Initialisation du contrôleur
        UserLoanHistoryController controller = loader.getController();
        controller.initData(selectedUser);

        // Affichage du nouveau Stage
        stage.show();
    }
    /**
     * Add a user.
     */
    @FXML
    private void userCurrentLoan() throws IOException {
        User selectedUser = adherantTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun adhérent sélectionné");
            alert.setContentText("Veuillez sélectionner un adhérent pour voir son historique d'emprunts");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Fxml/userCurrentLoan.fxml"));
        Parent userLoanHistoryParent = loader.load();

        Scene userLoanHistoryScene = new Scene(userLoanHistoryParent);

        // Création d'un nouveau Stage
        Stage stage = new Stage();
        stage.setScene(userLoanHistoryScene);

        // Initialisation du contrôleur
        UserCurrentLoanController controller = loader.getController();
        controller.initData(selectedUser);

        // Affichage du nouveau Stage
        stage.show();
    }

}