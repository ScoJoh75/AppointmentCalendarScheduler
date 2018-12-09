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

public class AppointmentScreen implements Initializable {
    @FXML
    private Button viewCustomer;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button appointmentUpdate;

    @FXML
    private Button appointmentAdd;

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
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLengthColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentLengthColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentTableView.setItems(allAppointments.getAllAppointments());
        appointmentTableView.getSelectionModel().select(0);
    }

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
    public void appointmentDeleteHandler(ActionEvent actionEvent) {

    }

    @FXML
    public void viewCustomerHandler(ActionEvent actionEvent) {

    }

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
    } // end sceneChange


} // end AppointmentScreen
