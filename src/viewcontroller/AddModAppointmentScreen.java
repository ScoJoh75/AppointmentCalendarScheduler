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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.ToDoubleBiFunction;

import static java.time.temporal.ChronoUnit.MINUTES;
import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class AddModAppointmentScreen implements Initializable {
    @FXML
    private Spinner<String> hourSpinner;

    @FXML
    private Spinner<String> minuteSpinner;

    @FXML
    private ChoiceBox<String> ampmField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addModButton;

    @FXML
    private Label addModLabel;

    @FXML
    private ChoiceBox<String> locationField;

    @FXML
    private ChoiceBox<String> titleField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private ChoiceBox<String> typeField;

    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private ChoiceBox<String> lengthField;

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
        setTimeSpinners();
        setChoiceBoxes();
    } // end initialize

    private void setTimeSpinners() {
        ObservableList<String> hrs = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        SpinnerValueFactory<String> hours = new SpinnerValueFactory.ListSpinnerValueFactory<>(hrs);
        hours.setWrapAround(true);
        hourSpinner.setValueFactory(hours);
        hourSpinner.getValueFactory().setValue("9");
        ObservableList<String> mins = FXCollections.observableArrayList("00", "15", "30", "45");
        SpinnerValueFactory<String> minutes = new SpinnerValueFactory.ListSpinnerValueFactory<>(mins);
        minutes.setWrapAround(true);
        minuteSpinner.setValueFactory(minutes);
        ampmField.getItems().setAll("AM", "PM");
        ampmField.setValue("AM");
    } // end setTimeSpinners

    private void setChoiceBoxes() {
        // set titleField Choice Box
        ObservableList<String> titles = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments.getAllAppointments()) {
            if (!titles.contains(appointment.getTitle())) {
                titles.add(appointment.getTitle());
            } // end if
        } // end for
        titleField.setItems(titles);
        titleField.setValue("Meeting");

        // set typeField Choice Box
        ObservableList<String> types = FXCollections.observableArrayList();
        for (Appointment appointent : allAppointments.getAllAppointments()) {
            if (!types.contains(appointent.getType())) {
                types.add(appointent.getType());
            } // end if
        } // end for
        typeField.setItems(types);
        typeField.setValue("First Meeting");

        // set locationField Choice Box
        ObservableList<String> locations = FXCollections.observableArrayList();
        locations.add("Online");
        for (Appointment appointment : allAppointments.getAllAppointments()) {
            if (!locations.contains(appointment.getLocation())) {
                locations.add(appointment.getLocation());
            } // end if
        } // end for
        locationField.setItems(locations);
        locationField.setValue("Online");

        // set lengthField Choice Box
        lengthField.setItems(FXCollections.observableArrayList("15", "30", "45", "60"));
        lengthField.setValue("60");

    } // end setChoiceBoxes

    @FXML
    void addModAppointmentHandler(ActionEvent event) {

    } // end addModAppointmentHandler

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
        addModButton.setText("Update");
        // Lines below set the on screen fields with the data in the passed appointment object
        titleField.setValue(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setValue(appointment.getLocation());
        typeField.setValue(appointment.getType());
        dateField.setValue(appointment.getStartTime().toLocalDate());
        // TODO Add setting of Hour, Minute, and AMPM Fields
        lengthField.setValue(String.valueOf(MINUTES.between(appointment.getStartTime().toLocalTime(), appointment.getEndTime().toLocalTime())));
        Customer customer = allCustomers.getCustomer(appointment.getCustomerId());
        customerTableView.getSelectionModel().select(customer);
        customerTableView.scrollTo(customer);
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
