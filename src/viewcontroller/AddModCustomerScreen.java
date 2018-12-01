package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;

import static viewcontroller.MainMenu.allCustomers;

public class AddModCustomerScreen {
    @FXML
    private Button cancelButton;

    @FXML
    private TextField phoneField;

    @FXML
    private Button addModButton;

    @FXML
    private Label addModLabel;

    @FXML
    private TextField cityField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField countryField;

    private Customer customer;
    private boolean modifying;
    private int index;

    @FXML
    void addModCustomerHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == addModButton) {
            // validate customer data, add to database, get id, create customer object and add to arraylist.
            sceneChange();
        } else {
            sceneChange();
        } // end if
    } // end addModCustomerHandler

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
        // Lines below set the on screen fields with the data in the passed customer object..
        addModLabel.setText("Update Customer");
        customerNameField.setText(customer.getCustomerName());
        address1Field.setText(customer.getAddress1());
        address2Field.setText(customer.getAddress2());
        cityField.setText(customer.getCityName());
        countryField.setText(customer.getCountryName());
        postalCodeField.setText(customer.getPostalCode());
        phoneField.setText(customer.getPhone());
        // determines the location in the AllCustomers array of the passed customer
        index = allCustomers.getAllCustomers().indexOf(customer);
    } // end setPart

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
