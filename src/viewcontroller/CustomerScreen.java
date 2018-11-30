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
    }

    @FXML
    void customerAddHandler(ActionEvent event) {

    }

    @FXML
    void customerUpdateHandler(ActionEvent event) {

    }

    @FXML
    void customerDeleteHandler(ActionEvent event) {

    }

    @FXML
    void mainMenuHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    } // mainMenuHandler
}
