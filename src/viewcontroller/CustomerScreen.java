package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerScreen {

    @FXML
    private Button customerDelete;

    @FXML
    private Button customerAdd;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private Button mainMenu;

    @FXML
    private Button customerUpdate;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> countryColumn;

    @FXML
    private TableView<?> customerTableView;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    void customerAddHandler(ActionEvent event) {

    }

    @FXML
    void customerUpdateHandler(ActionEvent event) {

    }

    @FXML
    void customerDeleteHandler(ActionEvent event) {

    }

    @FXML
    void mainMenuHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

}
