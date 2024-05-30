package cybooks.controller;

import cybooks.model.Loan;
import cybooks.service.LoanService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
/**
 * The controller for showing loans.
 */
public class ShowLoanController {

    @FXML
    private TableView<Loan> loanTable;

    @FXML
    private TableColumn<Loan, Integer> userIdColumn;

    @FXML
    private TableColumn<Loan, String> bookIdColumn;

    @FXML
    private TableColumn<Loan, String> loanDateColumn;

    @FXML
    private TableColumn<Loan, String> dueDateColumn;

    private LoanService loanService;

    public ShowLoanController() {
        loanService = new LoanService();
    }
    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        List<Loan> currentLoans = loanService.getCurrentLoans();
        loanTable.getItems().setAll(currentLoans);
    }
}