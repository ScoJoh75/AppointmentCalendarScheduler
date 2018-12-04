package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allCustomers;

public class CustomerScreen implements Initializable {

    @FXML
    private Button customerAdd;

    @FXML
    private Button customerUpdate;

    @FXML
    private Button customerDelete;

    @FXML
    private Button mainMenu;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> cityColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerTableView.setItems(allCustomers.getAllCustomers());
        customerTableView.getSelectionModel().select(0);
    } // end initialize

    @FXML
    void customerAddModHandler(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) customerAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModCustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(actionEvent.getSource() == customerUpdate) {
            AddModCustomerScreen controller = loader.getController();
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            controller.setCustomer(customer);
        } // end if
    } // end customerAddModHandler

    @FXML
    void customerDeleteHandler() {

    } // end customerDeleteHandler

    @FXML
    void mainMenuHandler() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    } // end mainMenuHandler
}
