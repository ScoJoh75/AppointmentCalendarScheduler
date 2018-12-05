package viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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

    @FXML
    void countryChangeHandler() {
        selectedCities = allLocations.getSelectedCities(countryField.getValue().getId());
        cityField.setItems(selectedCities);
    } // end countryChangeHandler

    @FXML
    void addModCustomerHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == cancelButton) {
            sceneChange();
        } else {
            // validate customer data, add to database, update customer object and update arraylist.
            addUpdateCustomer();
        } // end if
    } // end addModCustomerHandler

    private void addUpdateCustomer() throws IOException {
        // Get values from fields
        String customerName = customerNameField.getText();
        String address1 = address1Field.getText();
        String address2 = address2Field.getText();
        int cityId = cityField.getValue().getId();
        String cityName = cityField.getValue().getName();
        int countryId = countryField.getValue().getId();
        String countryName = countryField.getValue().getName();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();

        // if adding a new customer, instantiate a new Customer object.
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

        // Customer object is inserted into the AllCustomer list replacing the old or adding as new.
        if(modifying) {
            allCustomers.updateCustomer(customer, index);
        } else {
            allCustomers.addCustomer(customer);
        } // end if

        // Updates existing database entries or adds new
        try (Connection connection = DBConnection.dbConnect();
             Statement statement = connection.createStatement()){
            if(modifying) {
                // Update customer table
                String sql = "UPDATE customer SET customerName = '" + customerName + "', lastUpdateBy = '" + consultant.getUserName() + "' WHERE customerId = " + customer.getId();
                int customerUpdateSuccessful = statement.executeUpdate(sql);
                // Update address table
                sql = "UPDATE address SET address = '" + address1 + "', address2 = '" + address2 + "', cityId = '" + cityId + "', postalCode = '" + postalCode + "', phone = '" + phone + "', lastUpdateBy = '" + consultant.getUserName() + "' WHERE addressId = " + customer.getAddressId();
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
        } catch(SQLException e) {
            System.out.println("Error with your SQL");
        } // end try/catch

        // After update, return to the Customer Screen
        sceneChange();
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
    } // end setPart

    /**
     * sceneChange is just a simple method that brings you back to the CustomerScreen after Adding/Modding or just
     * plain cancelling out of the AddMod screen.
     */
    private void sceneChange() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addModButton.getScene().getWindow();
        stage.setTitle("Customer Database");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange
} // end AddModCustomerScreen
