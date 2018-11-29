package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {

    @FXML
    public TextField loginUN;
    @FXML
    private Label loginLabel;
    @FXML
    public PasswordField loginPW;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginSubmit;

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet results = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("languagefiles.Login", Locale.getDefault());
        loginLabel.setText(String.valueOf(rb.getObject("loginLabel")));
        passwordLabel.setText(String.valueOf(rb.getObject("passwordLabel")));
        loginSubmit.setText(String.valueOf(rb.getObject("loginSubmit")));
    } // end initialize


    @FXML
    public void processLogin(ActionEvent event) throws IOException, SQLException {
        System.out.println("I Clicked Submit!!!");

        conn = DBConnection.dbConnect();
        statement = conn.createStatement();
        results = statement.executeQuery("SELECT * FROM user");
        while(results.next()){
            System.out.println(results);
        }

        System.out.println(loginUN.getText().toLowerCase());
        System.out.println(loginPW.getText());
        loadMainMenu();
    } // end processLogin

    private void loadMainMenu() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) loginSubmit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Welcome, Please Select an Option Below.");
        stage.setScene(scene);
        stage.show();
    } // end loadMainMenu
} // end LoginScreen
