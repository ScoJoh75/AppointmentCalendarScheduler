package viewcontroller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class AppointmentScreen implements Initializable {

    @FXML
    private Button appointmentUpdate;

    @FXML
    private Button appointmentAdd;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button showWeekButton;

    @FXML
    private Button showMonthButton;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> timeColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> customerColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLengthColumn;

    // Using a Lambda to set the FilteredList Predicate makes the code far more efficient and compact.
    // Normally there is more code, an override and extra statements that need to be created and setup.
    private FilteredList<Appointment> filteredList = new FilteredList<>(allAppointments.getAllAppointments(), s -> true);
    // Also used a Lambda here to set the initial comparator for the SortedList.
    private SortedList<Appointment> sortedList = new SortedList<>(filteredList, Comparator.comparing(Appointment::getStartTime));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("localStartTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentLengthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLength"));
        appointmentTableView.setItems(sortedList);
        appointmentTableView.getSelectionModel().selectFirst();
    } // end initialize

    @FXML
    void appointmentAddModHandler(ActionEvent actionEvent) throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) appointmentAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModAppointmentScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(actionEvent.getSource() == appointmentUpdate) {
            AddModAppointmentScreen controller = loader.getController();
            Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
            controller.setAppointment(appointment);
        } // end if
    } // end choiceHandler

    @FXML
    public void appointmentDeleteHandler() {
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING: Delete Appointment??");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the appointment for " + appointment.getCustomerName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try (Connection connection = DBConnection.dbConnect()) {
                String Sql = "DELETE FROM appointment WHERE appointmentId = ?";
                PreparedStatement statement = connection.prepareStatement(Sql);
                statement.setInt(1, appointment.getId());

                statement.executeUpdate();

                if(allAppointments.removeAppointment(appointment)) {
                    System.out.println("Appointment was removed from the List!");
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Successfully Removed!");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment for: " + appointment.getCustomerName() + " has been successfully removed!");

                    alert.showAndWait();
                } // end if
            } catch (SQLException e) {
                System.out.println("Error with your SQL");
                System.out.println(e.getMessage());
            } // end try/catch
        } // end if
    } // end appointmentDeleteHandler

    @FXML
    public void viewCustomerHandler() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) appointmentAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModCustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer information");
        stage.show();
        AddModCustomerScreen controller = loader.getController();
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        Customer customer = allCustomers.getCustomer(appointment.getCustomerId());
        controller.viewCustomer(customer);
    } // end viewCustomerHandler

    @FXML
    // Using Lambdas to set the FilteredList Predicates makes the code far more efficient and compact.
    // Normally there is more code, an overrides and extra statements that need to be created and setup.
    private void filterListHandler(ActionEvent actionEvent) {
        if(actionEvent.getSource() == showWeekButton) {
            LocalDateTime weekFromNow = LocalDateTime.now().plusDays(7);
            filteredList.setPredicate(s -> s.getStartTime().toLocalDateTime().isBefore(weekFromNow));
            appointmentTableView.getSelectionModel().selectFirst();
        } else if(actionEvent.getSource() == showMonthButton) {
            LocalDateTime monthFromNow = LocalDateTime.now().plusMonths(1);
            filteredList.setPredicate(s -> s.getStartTime().toLocalDateTime().isBefore(monthFromNow));
            appointmentTableView.getSelectionModel().selectFirst();
        } else {
            filteredList.setPredicate(s -> true);
            appointmentTableView.getSelectionModel().selectFirst();
        } // end if
    } // end filterListHandler

    @FXML
    private void mainMenuHandler() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.setTitle("Main Menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end mainMenuHandler
} // end AppointmentScreen
