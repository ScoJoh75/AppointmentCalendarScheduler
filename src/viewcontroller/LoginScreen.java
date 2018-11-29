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

    private static Consultant consultant = new Consultant();
    private static Connection connection = null;
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
    public void processLogin() throws SQLException {
        System.out.println("I Clicked Submit!!!");

        connection = DBConnection.dbConnect();
        try {
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT userId FROM user WHERE userName = '" + loginUN.getText().toLowerCase() + "'");
            ResultSetMetaData rsmd = results.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (results.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = results.getString(i);
                    consultant.setId(Integer.parseInt(columnValue));
                    consultant.setUserName(loginUN.getText());
                } // end for
                System.out.println();
            } // end while
        } catch(SQLException e) {
            System.out.println("A SQL Error has occurred!");
            e.printStackTrace();
        } finally {
            if(connection != null) connection.close();
        } // end try

        System.out.println(consultant.getId());
        System.out.println(consultant.getUserName());

        try {
            loadMainMenu();
        } catch(IOException e) {
            System.out.println("An error occurred when opening Main Menu!");
            e.printStackTrace();
        } // end try


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
