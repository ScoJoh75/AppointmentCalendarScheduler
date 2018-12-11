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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static viewcontroller.AddModCustomerScreen.allLocations;
import static viewcontroller.LoginScreen.consultant;

public class MainMenu implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button customerButton;

    @FXML
    private Button appointmentButton;
    
    @FXML
    private Label appointmentAlert;

    public static AllCustomers allCustomers = new AllCustomers();
    static AllAppointments allAppointments = new AllAppointments();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome " + consultant.getUserName());
        // Gets all Customers and Populates the AllCustomers list when the MainMenu loads for the first time
        if(allCustomers.getAllCustomers().size() == 0) {
            buildLocationList();
            buildCustomerList();
            buildAppointmentList();
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
    } // end choiceHandler

    private void buildCustomerList(){
        try (Connection connection = DBConnection.dbConnect();
            Statement statement = connection.createStatement()){
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
                    "customer.active = 1\n" +
                    "ORDER BY customer.customerId ASC;";
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                int Id = results.getInt("customerId");
                String customerName = results.getString("customerName");
                int active = results.getInt("active");
                int addressId = results.getInt("addressId");
                String address = results.getString("address");
                String address2 = results.getString("address2");
                String postalCode = results.getString("postalCode");
                String phone = results.getString("phone");
                int cityId = results.getInt("cityId");
                String cityName = results.getString("city");
                int countryId = results.getInt("countryId");
                String countryName = results.getString("country");
                allCustomers.addCustomer(new Customer(Id, customerName, active, addressId, address, address2, postalCode, phone, cityId, cityName, countryId, countryName));
            } // end while
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    } // end buildCustomerList

    /**
     * buildLocationList runs when the MainMenu screen is loaded the first time. It creates an observable list for
     * Cities and one for Countries. The lists are made up of Country and City objects respectively which contain
     * the database ID and name. The Country objects also contain the countryId to easily link them to their parent
     * country.
     */
    private void buildLocationList() {
        try (Connection connection = DBConnection.dbConnect();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT countryId, country FROM country ORDER BY countryId ASC";
            ResultSet results = statement.executeQuery(sql);
            while(results.next()) {
                Country country = new Country(results.getInt("countryId"), results.getString("country"));
                allLocations.addCountry(country);
            } // end while

            sql = "SELECT cityId, city, countryId FROM city ORDER BY cityId ASC";
            results = statement.executeQuery(sql);
            while(results.next()) {
                City city = new City(results.getInt("cityId"), results.getString("city"), results.getInt("countryId"));
                allLocations.addCity(city);
            } // end while

        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    } // end buildLocationList

    /**
     * buildAppointmentList runs when the MainMenu screen is loaded the first time. It pulls data from the database and then
     * tt creates an observable list for Appointments. This list is utilized by the AppointmentScreen
     */
    private void buildAppointmentList() {
        try (Connection connection = DBConnection.dbConnect();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT appointmentId, \n" +
                    "customerId, \n" +
                    "userId, \n" +
                    "title, \n" +
                    "description, \n" +
                    "location,\n" +
                    "contact,\n" +
                    "type, \n" +
                    "start, \n" +
                    "end\n" +
                    "FROM appointment\n" +
                    //"WHERE userId = " + consultant.getId() + "\n" +
                    "ORDER BY appointmentId";
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                int appointmentId = results.getInt("appointmentId");
                int customerId = results.getInt("customerId");
                int userId = results.getInt("userId");
                String title = results.getString("title");
                String description = results.getString("description");
                String location = results.getString("location");
                String contact = results.getString("contact");
                String type = results.getString("type");
                LocalDateTime localStartTime = results.getObject("start", LocalDateTime.class);
                ZonedDateTime start = ZonedDateTime.of(localStartTime, ZoneId.systemDefault());
                LocalDateTime localEndTime = results.getObject("end", LocalDateTime.class);
                ZonedDateTime end = ZonedDateTime.of(localEndTime, ZoneId.systemDefault());
                allAppointments.addAppointment(new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, start, end));
            } // end while
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    } // end buildAppointmentList
} // end MainMenu