package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button customerButton;

    @FXML
    private Button appointmentButton;
    
    @FXML
    private Label appointmentAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome " + LoginScreen.consultant.getUserName());
    } // end initialize

    public void choiceHandler(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        String choice;

        stage = (Stage) customerButton.getScene().getWindow();

        if (actionEvent.getSource() == customerButton) {
            choice = "CustomerScreen";
            stage.setTitle("Customer Database");
        } else if (actionEvent.getSource() == appointmentButton) {
            choice = "AppointmentScreen";
            stage.setTitle("Appointment Database");
        } else {
            choice = "ReportScreen";
            stage.setTitle("System Reports");
        } // end if

        FXMLLoader loader = new FXMLLoader(getClass().getResource(choice + ".fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end customerHandler
} // end MainMenu