package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportScreen {

    @FXML
    private Button reportAButton;

    @FXML
    private Button reportBButton;
    
    @FXML
    private Button reportCButton;

    public void reportHandler(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) reportAButton.getScene().getWindow();

        if (actionEvent.getSource() == reportAButton) {
            System.out.println("Running Report A");
        } else if (actionEvent.getSource() == reportBButton) {
            System.out.println("Running Report B");
        } else if (actionEvent.getSource() == reportCButton){
            System.out.println("Running Report C");
        } else {
            stage.setTitle("Main Menu");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end if
    } // end customerHandler
} // end MainMenu