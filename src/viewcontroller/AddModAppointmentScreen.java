package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class AddModAppointmentScreen implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button addModButton;

    @FXML
    private Label addModLabel;

    @FXML
    private ComboBox<?> locationField;

    @FXML
    private ComboBox<?> titleField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<String> timeField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private ComboBox<?> typeField;

    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private ComboBox<?> lengthField;

    private Appointment appointment;
    private boolean modifying;
    private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerTableView.setItems(allCustomers.getAllCustomers());
        customerTableView.getSelectionModel().select(0);
    }

    @FXML
    void addModAppointmentHandler(ActionEvent event) {

    }

    /**
     * setAppointment is called when a user decides to update an existing appointment.
     * The appointment selected in the appointment table will be passed into this method. Then
     * the values of the fields as they pertain to the appointment selected will be populated.
     * The 'modifying' variable which determines the behavior of the save button is set as well.
     * @param appointment the appointment selected in the appointment table when update was clicked.
     */
    void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        modifying = true;
        addModLabel.setText("Update Appointment");
        addModButton.setText("Update Appointment");
        // Lines below set the on screen fields with the data in the passed customer object..
//        customerNameField.setText(customer.getCustomerName());
//        address1Field.setText(customer.getAddress1());
//        address2Field.setText(customer.getAddress2());
//        cityField.setValue(allLocations.getAllCities().get(customer.getCityId()-1));
//        countryField.setValue(allLocations.getAllCountries().get(customer.getCountryId()-1));
//        postalCodeField.setText(customer.getPostalCode());
//        phoneField.setText(customer.getPhone());
        // determines the location in the AllCustomers array of the passed customer
        index = allAppointments.getAllAppointments().indexOf(appointment);
    } // end setCustomer

    /**
     * sceneChange is just a simple method that brings you back to the AppointmentScreen after Adding/Modding or just
     * plain cancelling out of the AddMod screen.
     */
    @FXML
    private void sceneChange() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addModButton.getScene().getWindow();
        stage.setTitle("Appointment Database");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange
} // end AddModAppointmentScreen
