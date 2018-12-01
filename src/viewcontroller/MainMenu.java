package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.AllCustomers;
import model.Customer;
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button customerButton;

    @FXML
    private Button appointmentButton;
    
    @FXML
    private Label appointmentAlert;

    static AllCustomers allCustomers = new AllCustomers();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome " + LoginScreen.consultant.getUserName());
        // Gets all Customers and Populates the AllCustomers list when the MainMenu loads for the first time
        if(allCustomers.getAllCustomers().size() == 0) {
            buildCustomerList();
        } // end if

        // Checks for appointments in the next 15 minutes and posts an alert notification
        appointmentAlert.visibleProperty().setValue(false);
    } // end initialize

    public void choiceHandler(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        String choice;

        stage = (Stage) customerButton.getScene().getWindow();

        if (actionEvent.getSource() == customerButton) {
            choice = "CustomerScreen";
            stage.setTitle("Customer Database");
        } else if (actionEvent.getSource() == appointmentButton) {
            choice = "AppointmentScreen";
            stage.setTitle("Appointment Database");
        } else {
            choice = "ReportScreen";
            stage.setTitle("System Reports");
        } // end if

        FXMLLoader loader = new FXMLLoader(getClass().getResource(choice + ".fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end customerHandler

    private void buildCustomerList(){
        try (Connection connection = DBConnection.dbConnect()){
            Statement statement = connection.createStatement();
            String sql = "SELECT customer.customerId, \n" +
                    "customer.customerName, \n" +
                    "customer.active, \n" +
                    "customer.addressId, \n" +
                    "address.address, \n" +
                    "address.address2, \n" +
                    "address.postalCode, \n" +
                    "address.phone, \n" +
                    "address.cityId, \n" +
                    "city.city, \n" +
                    "city.countryId, \n" +
                    "country.country\n" +
                    "FROM customer, address, city, country\n" +
                    "WHERE customer.addressId = address.addressId AND\n" +
                    "address.cityId = city.cityId AND\n" +
                    "city.countryId = country.countryId AND\n" +
                    "customer.active = 1;";
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                Customer customer = new Customer();
                customer.setId(results.getInt("customerId"));
                customer.setCustomerName(results.getString("customerName"));
                customer.setActive(results.getInt("active"));
                customer.setAddressId(results.getInt("addressId"));
                customer.setAddress1(results.getString("address"));
                customer.setAddress2(results.getString("address2"));
                customer.setPostalCode(results.getString("postalCode"));
                customer.setPhone(results.getString("phone"));
                customer.setCityId(results.getInt("cityId"));
                customer.setCityName(results.getString("city"));
                customer.setCountryId(results.getInt("countryId"));
                customer.setCountryName(results.getString("country"));
                allCustomers.addCustomer(customer);
            } // end while

        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    }
} // end MainMenu