package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentScreen implements Initializable {

    @FXML
    private Button mainMenuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
