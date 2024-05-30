// LoanHistoryController.java
package cybooks.controller;

import cybooks.model.Loan;
import cybooks.model.DB;
import cybooks.model.User;
import cybooks.service.LoanService;
import cybooks.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller for the loan history.
 */
public class UserLoanHistoryController implements Initializable {

    @FXML
    private TableView<Loan> loanHistoryTableView;
    @FXML
    private TableColumn<Loan, String> bookIsbnColumn;
    @FXML
    private TableColumn<Loan, String> loanDateColumn;
    @FXML
    private TableColumn<Loan, String> returnDateColumn;

    private DB db = new DB();
    private LoanService loanService = new LoanService();
    /**
     * Initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    }
    /**
     * Initialize the data.
     * @param user The user.
     */
    public void initData(User user) {
        List<Loan> userLoanHistory = loanService.getUserHistoryLoans(user.getId());
        loanHistoryTableView.getItems().addAll(userLoanHistory);
    }

}