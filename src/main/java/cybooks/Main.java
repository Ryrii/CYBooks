package cybooks;

import cybooks.model.Loan;
import cybooks.service.*;
import cybooks.model.DB;

import java.sql.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main method.
     * @param args The arguments.
     */
    public static void main(String[] args) {
        DB db = new DB();
        db.initializeDatabase();
        UserService userService = new UserService();
        LoanService loanService = new LoanService();

        userService.addUser("Sofie", "Lefevre", "0477777777", "zqeez", "rue de la rue", 4000);
        Loan loa = new Loan(1, 1, "9782807603691", new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 60), new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30), null);
        db.addLoan(loa);
        launch();
    }
}