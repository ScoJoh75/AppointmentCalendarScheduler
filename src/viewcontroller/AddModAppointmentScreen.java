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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static viewcontroller.LoginScreen.consultant;
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
        dateField.setValue(LocalDate.now());
    } // end initialize

    private void setTimeSpinners() {
        ObservableList<String> hrs = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
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
        for (Appointment appointment : allAppointments.getAllAppointments()) {
            if (!types.contains(appointment.getType())) {
                types.add(appointment.getType());
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
    void addModAppointmentHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == cancelButton) {
            sceneChange();
        } else {
            // validate appointment data, add to database, update appointment object and update arraylist.
            addUpdateAppointment();
        } // end if
    } // end addModAppointmentHandler

    private void addUpdateAppointment() {
        // Get values from fields
        int customerID = customerTableView.getSelectionModel().getSelectedItem().getId();
        String customerName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
        int conultantId = consultant.getId();
        String title = titleField.getValue();
        String description = descriptionField.getText();
        String location = locationField.getValue();
        String contact = consultant.getUserName();
        String type = typeField.getValue();
        String appointmentLength = lengthField.getValue();
        LocalDate localDate = dateField.getValue();
        LocalTime localTime = LocalTime.parse(hourSpinner.getValue() + ":" + minuteSpinner.getValue() + " " + ampmField.getValue(), DateTimeFormatter.ofPattern("hh:mm a"));
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime startTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        ZonedDateTime endTime = startTime.plusMinutes(Integer.parseInt(appointmentLength));

        System.out.println(startTime);
        //System.out.println("In UTC the same time is: " + startTime.withZoneSameInstant(ZoneOffset.UTC));
        System.out.println(endTime);

    } // end addUpdateAppointment

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
        int hour = appointment.getStartTime().toLocalTime().getHour();
        hourSpinner.getValueFactory().setValue(String.valueOf(hour));
        if(hour >= 12) ampmField.setValue("PM");
        minuteSpinner.getValueFactory().setValue(String.valueOf(appointment.getStartTime().toLocalTime().getMinute()));
        lengthField.setValue(appointment.getAppointmentLength());
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
