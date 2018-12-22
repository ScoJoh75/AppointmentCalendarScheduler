package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class CustomerScreen implements Initializable {

    @FXML
    private Button customerAdd;

    @FXML
    private Button customerUpdate;

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
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        // Remove customer from the current all customers list
        allCustomers.removeCustomer(customer);
        // Remove all of customers appointments from the current all appointments list
        Iterator iterator = allAppointments.getAllAppointments().iterator();
        while(iterator.hasNext()) {
            Appointment appointment = (Appointment)iterator.next();
            if(customer.getId() == appointment.getCustomerId()) {
                iterator.remove();
            } // end if
        } // end while

        // connect to the database and set the customer as inactive
        try (Connection connection = DBConnection.dbConnect();
             Statement statement = connection.createStatement()){
            String sql = "UPDATE customer SET active = 0 WHERE customerId = " + customer.getId();
            int removeCustomer = statement.executeUpdate(sql);
            if (removeCustomer == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Successfully Removed");
                alert.setHeaderText(null);
                alert.setContentText(customer.getCustomerName() + " and any related appointments have been removed!");
                alert.showAndWait();
            } // end if
        } catch(SQLException e) {
            System.out.println("Error with your SQL");
        } // end try/catch
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
