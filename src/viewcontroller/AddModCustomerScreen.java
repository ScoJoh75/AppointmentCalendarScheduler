package viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static viewcontroller.LoginScreen.consultant;
import static viewcontroller.MainMenu.allCustomers;

public class AddModCustomerScreen implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private TextField phoneField;

    @FXML
    private Button addModButton;

    @FXML
    private Label addModLabel;

    @FXML
    private ComboBox<City> cityField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField address1Field;

    @FXML
    private ComboBox<Country> countryField;

    private Customer customer;
    private boolean modifying;
    private int index;

    static AllLocations allLocations = new AllLocations();
    private ObservableList<City> selectedCities = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboBoxes();
    }

    /**
     * loadComboBoxes sets up the needed String converter and toString overrides as well
     * as binds the appropriate list to the combo boxes
     */
    private void loadComboBoxes() {
        countryField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Country country) {
                return country.getName();
            }

            @Override
            public Country fromString(String string) {
                return null;
            }
        });
        cityField.setConverter(new StringConverter<>() {
            @Override
            public String toString(City city) {
                return city.getName();
            }

            @Override
            public City fromString(String string) {
                return null;
            }
        });
        countryField.setItems(allLocations.getAllCountries());
        cityField.setItems(selectedCities);
    } // end loadComboBoxes

    /**
     * countryChangeHandler updates the cities that are populated in the city combobox
     * based on the value in the country combobox. This ensures that the city/country
     * pair will always be consistent and correct.
     */
    @FXML
    void countryChangeHandler() {
        selectedCities = allLocations.getSelectedCities(countryField.getValue().getId());
        cityField.setItems(selectedCities);
    } // end countryChangeHandler


    /**
     * addModCustomerHandler simply determines which button was pressed by the user and calls the
     * method appropriate to that selection.
     */
    @FXML
    void addModCustomerHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == cancelButton) {
            sceneChange();
        } else {
            // validate customer data, add to database, update customer object and update arraylist.
            addUpdateCustomer();
        } // end if
    } // end addModCustomerHandler

    /**
     * addUpdateCustomer is called when the user clicks the add/update button.
     * It either updates an existing customer object, or creates a new one and adds
     * the field values to the object. Then it connects to the database and either updates
     * or inserts the new values for the customer depending on the situation.
     */
    private void addUpdateCustomer() throws IOException {
        // Get values from fields
        String customerName = customerNameField.getText();
        String address1 = address1Field.getText();
        String address2 = address2Field.getText();
        int cityId = 0;
        String cityName = "";
        if(cityField.getValue() != null) {
            cityId = cityField.getValue().getId();
            cityName = cityField.getValue().getName();
        } // end if
        int countryId = 0;
        String countryName = "";
        if(countryField.getValue() != null) {
            countryId = countryField.getValue().getId();
            countryName = countryField.getValue().getName();
        } // end if
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();

        // if adding a new customer, instantiate a new Customer object and set Id values
        // TODO Long term: modify the way ID's are assigned to be similar to how appointment ID's are assigned,
        //  rather than by the length of the array list which if customers are deleted could put things out of sync.
        if(!modifying) {
            this.customer = new Customer();
            int Id = allCustomers.getAllCustomers().size() + 1;
            customer.setId(Id);
            int addressId = allCustomers.getAllCustomers().size() + 1;
            customer.setAddressId(addressId);
            customer.setActive(1);
        } // end if

        // Update customer object with values from fields
        customer.setCustomerName(customerName);
        customer.setAddress1(address1);
        customer.setAddress2(address2);
        customer.setCityName(cityName);
        customer.setCityId(cityId);
        customer.setCountryName(countryName);
        customer.setCountryId(countryId);
        customer.setPostalCode(postalCode);
        customer.setPhone(phone);

        boolean valid = customer.validate();

        if(valid) {
            // Customer object is inserted into the AllCustomer array list replacing the old or adding as new.
            if (modifying) {
                allCustomers.updateCustomer(customer, index);
            } else {
                allCustomers.addCustomer(customer);
            } // end if

            // Updates existing database entries or adds new
            // TODO convert to prepared statements in this section
            try (Connection connection = DBConnection.dbConnect();
                 Statement statement = connection.createStatement()) {
                if (modifying) {
                    // Update customer table
                    String sql = "UPDATE customer " +
                            "SET customerName = '" + customerName +
                            "', lastUpdateBy = '" + consultant.getUserName() +
                            "' WHERE customerId = " + customer.getId();
                    int customerUpdateSuccessful = statement.executeUpdate(sql);
                    // Update address table
                    sql = "UPDATE address " +
                            "SET address = '" + address1 +
                            "', address2 = '" + address2 +
                            "', cityId = '" + cityId +
                            "', postalCode = '" + postalCode +
                            "', phone = '" + phone +
                            "', lastUpdateBy = '" + consultant.getUserName() +
                            "' WHERE addressId = " + customer.getAddressId();
                    int addressUpdateSuccessful = statement.executeUpdate(sql);
                    // check if updates were successful
                    if (customerUpdateSuccessful == 1 && addressUpdateSuccessful == 1) {
                        System.out.println("Congrats, You've updated a customer successfully!!!!!");
                    } // end if
                } else {
                    // Add data to Address table
                    String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) \n" +
                            "VALUES ('" + address1 + "', \n" +
                            "'" + address2 + "', \n" +
                            cityId + ", \n" +
                            "'" + postalCode + "', \n" +
                            "'" + phone + "', \n" +
                            "CURRENT_TIMESTAMP, \n" +
                            "'" + consultant.getUserName() + "', \n" +
                            "'" + consultant.getUserName() + "');";
                    int addressUpdateSuccessful = statement.executeUpdate(sql);
                    // Add data to Customer table
                    sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) \n" +
                            "VALUES ('" + customerName + "', \n" +
                            customer.getAddressId() + ", \n" +
                            "1, \n" +
                            "CURRENT_TIMESTAMP, \n" +
                            "'" + consultant.getUserName() + "', \n" +
                            "'" + consultant.getUserName() + "');";
                    int customerUpdateSuccessful = statement.executeUpdate(sql);
                    // check if inserts were successful
                    if (customerUpdateSuccessful == 1 && addressUpdateSuccessful == 1) {
                        System.out.println("Congrats, you've added a new customer successfully!!!!!");
                    } // end if
                } // end if
            } catch (SQLException e) {
                System.out.println("Error with your SQL");
            } // end try/catch

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Successfully Added!");
            alert.setHeaderText(null);
            alert.setContentText(customerName + " has been successfully added!");

            alert.showAndWait();

            // After update, return to the Customer Screen
            sceneChange();
        } // end if
    } // end addUpdateCustomer

    /**
     * setCustomer is called when a user decides to update an existing customer.
     * The customer selected in the customer table will be passed into this method. Then
     * the values of the fields as they pertain to the customer selected will be populated.
     * The 'modifying' variable which determines the behavior of the save button is set as well.
     * @param customer the customer selected in the customer table when update was clicked.
     */
    void setCustomer(Customer customer) {
        this.customer = customer;
        modifying = true;
        addModLabel.setText("Update Customer");
        addModButton.setText("Update Customer");
        // Lines below set the on screen fields with the data in the passed customer object..
        customerNameField.setText(customer.getCustomerName());
        address1Field.setText(customer.getAddress1());
        address2Field.setText(customer.getAddress2());
        cityField.setValue(allLocations.getAllCities().get(customer.getCityId()-1));
        countryField.setValue(allLocations.getAllCountries().get(customer.getCountryId()-1));
        postalCodeField.setText(customer.getPostalCode());
        phoneField.setText(customer.getPhone());
        // determines the location in the AllCustomers array of the passed customer
        index = allCustomers.getAllCustomers().indexOf(customer);
    } // end setCustomer

    /**
     * viewCustomer is called when a user clicks on the viewCustomer button in appointments.
     * It populates the field with the customer information, but disables all fields for modification.
     * TODO Possibly add the functionality to give the user the option to update a customers information
     * @param customer
     */
    void viewCustomer(Customer customer) {
        this.customer = customer;
        addModLabel.setText("Customer Information");
        addModButton.setVisible(false);
        customerNameField.setText(customer.getCustomerName());
        customerNameField.setEditable(false);
        address1Field.setText(customer.getAddress1());
        address1Field.setEditable(false);
        address2Field.setText(customer.getAddress2());
        address2Field.setEditable(false);
        cityField.setValue(allLocations.getAllCities().get(customer.getCityId()-1));
        cityField.setDisable(true);
        countryField.setValue(allLocations.getAllCountries().get(customer.getCountryId()-1));
        countryField.setDisable(true);
        postalCodeField.setText(customer.getPostalCode());
        postalCodeField.setEditable(false);
        phoneField.setText(customer.getPhone());
        phoneField.setEditable(false);
        cancelButton.setText("Return to Appointments");
    } // end viewCustomer

    /**
     * sceneChange is just a simple method that brings you back to the CustomerScreen after Adding/Modding or just
     * plain cancelling out of the AddMod screen.
     */
    private void sceneChange() throws IOException {
        String destination;
        Stage stage;
        Parent root;
        stage = (Stage) addModButton.getScene().getWindow();
        // Determine the screen we're going back to
        if(addModButton.isVisible()) {
            destination = "CustomerScreen.fxml";
            stage.setTitle("Customer Database");
        } else {
            destination = "AppointmentScreen.fxml";
            stage.setTitle("Appointment Database");
        } //end if
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange
} // end AddModCustomerScreen
