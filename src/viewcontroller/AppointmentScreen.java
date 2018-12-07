package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class AppointmentScreen implements Initializable {

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> timeColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> customerNameColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLengthColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerNameColumn.setCellValueFactory(allCustomers.getCustomer(1).getCustomerName()));
//        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>(allCustomers.getCustomer("customerId").getCustomerName()));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentLengthColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentTableView.setItems(allAppointments.getAllAppointments());
        appointmentTableView.getSelectionModel().select(0);
    }

    @FXML
    void choiceHandler(ActionEvent event) throws IOException{
        sceneChange();
    } // end choiceHandler

    private void sceneChange() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.setTitle("Main Menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange

} // end AppointmentScreen
