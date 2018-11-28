package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button customerButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button appointmentButton;

    @FXML
    private Label appointmentAlert;

    public void customerHandler(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) customerButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Database");
        stage.setScene(scene);
        stage.show();
    }
}