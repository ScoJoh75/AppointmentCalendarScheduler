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
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
    private Label ampmLabel;

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
        hourSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals("09") || newValue.equals("10") || newValue.equals("11")) {
                ampmLabel.setText("AM");
            } else {
                ampmLabel.setText("PM");
            } // end if
        });
    } // end initialize

    private void setTimeSpinners() {
        ObservableList<String> hrs = FXCollections.observableArrayList("09", "10", "11", "12", "01", "02", "03", "04", "05");
        SpinnerValueFactory<String> hours = new SpinnerValueFactory.ListSpinnerValueFactory<>(hrs);
        hours.setWrapAround(true);
        hourSpinner.setValueFactory(hours);
        hourSpinner.getValueFactory().setValue("09");
        ObservableList<String> mins = FXCollections.observableArrayList("00", "15", "30", "45");
        SpinnerValueFactory<String> minutes = new SpinnerValueFactory.ListSpinnerValueFactory<>(mins);
        minutes.setWrapAround(true);
        minuteSpinner.setValueFactory(minutes);
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

    private void addUpdateAppointment() throws IOException{
        // Get values from fields
        int customerID = customerTableView.getSelectionModel().getSelectedItem().getId();
        int consultantId = consultant.getId();
        String title = titleField.getValue();
        String description = descriptionField.getText();
        String location = locationField.getValue();
        String contact = consultant.getUserName();
        String type = typeField.getValue();
        String appointmentLength = lengthField.getValue();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S a");
        LocalDateTime localDateTime = LocalDateTime.parse(dateField.getValue() + " " + hourSpinner.getValue() + ":" + minuteSpinner.getValue() + ":00.0 " + ampmLabel.getText(), df);
        ZoneId localId = ZoneId.systemDefault();
        ZonedDateTime localStartTime = localDateTime.atZone(localId);
        ZonedDateTime localEndTime = localStartTime.plusMinutes(Integer.parseInt(appointmentLength));

        // create new appointment object if we're not modifying
        if (!modifying) {
            this.appointment = new Appointment();
        } // end if

        // Update appointment object with values from fields
        appointment.setCustomerId(customerID);
        appointment.setCustomerName();
        appointment.setConsultantId(consultantId);
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContact(contact);
        appointment.setType(type);
        appointment.setAppointmentLength(appointmentLength);
        appointment.setStartTime(localStartTime);
        appointment.setLocalStartTime();
        appointment.setEndTime(localEndTime);

        // TODO For future: clean up duplicate code by changing the order and layout of the SQL and Prepared Statements
        try (Connection connection = DBConnection.dbConnect()) {
            if (modifying) {
                // update appointment table
                String Sql = "UPDATE appointment " +
                        "SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, " +
                        "contact = ?, type = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?";
                PreparedStatement statement = connection.prepareStatement(Sql);
                statement.setInt(1, customerID);
                statement.setInt(2, consultantId);
                statement.setString(3, title);
                statement.setString(4, description);
                statement.setString(5, location);
                statement.setString(6, contact);
                statement.setString(7, type);
                statement.setObject(8, Timestamp.valueOf(localStartTime.toLocalDateTime()));
                statement.setObject(9, Timestamp.valueOf(localEndTime.toLocalDateTime()));
                statement.setObject(10, new Timestamp(System.currentTimeMillis()));
                statement.setString(11, contact);
                statement.setInt(12, appointment.getId());

                statement.executeUpdate();
            } else {
                // Add a new appointment
                String Sql = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(Sql);
                statement.setInt(1, customerID); //customerId
                statement.setInt(2, consultantId); //userId
                statement.setString(3, title); //title
                statement.setString(4, description); //description
                statement.setString(5, location); //location
                statement.setString(6, contact); //contact
                statement.setString(7, type); //type
                statement.setObject(8, Timestamp.valueOf(localStartTime.toLocalDateTime())); //start
                statement.setObject(9, Timestamp.valueOf(localEndTime.toLocalDateTime())); //end
                statement.setObject(10, new Timestamp(System.currentTimeMillis())); // createDate
                statement.setString(11, contact); //createdBy
                statement.setObject(12, new Timestamp(System.currentTimeMillis())); //lastUpdate
                statement.setString(13, contact); //lastUpdatedBy

                statement.executeUpdate();

                // Get appointment Id for the newly inserted appointment
                Sql = "SELECT MAX(appointmentId) FROM appointment WHERE customerId = ?";
                statement = connection.prepareStatement(Sql);
                statement.setInt(1, customerID);
                ResultSet results = statement.executeQuery();
                while (results.next()) {
                    int Id = results.getInt("MAX(appointmentId)");
                    appointment.setId(Id);
                } // end while
            } // end if
        } catch (SQLException e) {
            System.out.println("Error with your SQL");
            System.out.println(e.getMessage());
        } // end try/catch

        // Appointment object is inserted into the AllAppointment list replacing the old or adding as new.
        if (modifying) {
            allAppointments.updateAppointment(appointment, index);
        } else {
            allAppointments.addAppointment(appointment);
        } // end if

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Successfully Added!");
        alert.setHeaderText(null);
        alert.setContentText("Appointment on: " + appointment.getLocalStartTime().substring(0,10) + "\n" +
                "at: " + appointment.getLocalStartTime().substring(13) + "\n" +
                " with: " + appointment.getCustomerName() + " has been successfully added!");
        alert.showAndWait();

        sceneChange();

    } // end addUpdateAppointment

    /**
     * setAppointment is called when a user decides to update an existing appointment.
     * The appointment selected in the appointment table will be passed into this method. Then
     * the values of the fields as they pertain to the appointment selected will be populated.
     * The 'modifying' variable which determines the behavior of the save button is set as well.
     *
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
        String hour = appointment.getLocalStartTime().substring(13,15);
        hourSpinner.getValueFactory().setValue(hour);
        //if (hour >= 12) ampmLabel.setText("PM");
        String minute = appointment.getLocalStartTime().substring(17,19);
        minuteSpinner.getValueFactory().setValue(minute);
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
