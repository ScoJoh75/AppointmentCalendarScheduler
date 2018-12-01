package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModCustomerScreen {
    @FXML
    private Button cancelButton;

    @FXML
    private TextField phoneField;

    @FXML
    private Button addModButton;

    @FXML
    private Label addModLabel;

    @FXML
    private TextField cityField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField countryField;

    @FXML
    void addModCustomerHandler(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == addModButton) {
            // validate customer data, add to database, get id, create customer object and add to arraylist.
            sceneChange();
        } else {
            sceneChange();
        } // end if
    } // end addModCustomerHandler

    private void sceneChange() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addModButton.getScene().getWindow();
        stage.setTitle("Customer Database");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange

} // end AddModCustomerScreen
