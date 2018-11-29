package viewcontroller;

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
import model.Consultant;
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    static Consultant consultant = new Consultant();
    static Connection connection = null;
    Statement statement = null;
    ResultSet results = null;
    private String password;
    private boolean validated = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("languagefiles.Login", Locale.getDefault());
        loginLabel.setText(String.valueOf(rb.getObject("loginLabel")));
        passwordLabel.setText(String.valueOf(rb.getObject("passwordLabel")));
        loginSubmit.setText(String.valueOf(rb.getObject("loginSubmit")));
    } // end initialize


    @FXML
    public void processLogin() throws SQLException {
        System.out.println("I Clicked Submit!!!");

        connection = DBConnection.dbConnect();
        try {
            statement = connection.createStatement();
            String sql = "SELECT userId, password FROM user WHERE userName = '" + loginUN.getText().toLowerCase() + "'";
            System.out.println(sql);
            results = statement.executeQuery(sql);

            while (results.next()) {
                consultant.setId(Integer.parseInt(results.getString(0)));
                consultant.setUserName(loginUN.getText());
                password = results.getString(1);
            } // end while

            if(password.equals(loginPW.getText()) && Integer.parseInt(results.getString(0)) > 0) validated = true;

        } catch(SQLException e) {
            System.out.println("A SQL Error has occurred!");
        } finally {
            if(connection != null) connection.close();
        } // end try



        if(validated) {
            try {
                loadMainMenu();
            } catch (IOException e) {
                System.out.println("An error occurred when opening Main Menu!");
            } // end try
        } else {
            System.out.println("Ya UName or PWord be incorrect!!!");
        }

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
