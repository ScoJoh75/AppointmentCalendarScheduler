package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static viewcontroller.MainMenu.allCustomers;

public class ReportOutputScreen {

    @FXML
    private Button reportAButton;

    @FXML
    private Button reportBButton;

    @FXML
    private Button reportCButton;

    @FXML
    private TextArea reportText;

    @FXML
    private Button backButton;

    public void runReportHandler(ActionEvent actionEvent) {
        String sql = "";
        if(actionEvent.getSource() == reportAButton) {
            reportText.clear();
            sql = "SELECT MONTH(start) AS Month, YEAR(start) AS Year, type AS Type, count(type) AS Total FROM appointment GROUP BY Type, Month ORDER BY Year, Month, Type, Month ASC\n";
            reportText.appendText("Appointments by Type by Month\n");
        } else if (actionEvent.getSource() == reportBButton) {
            reportText.clear();
            sql = "SELECT * FROM appointment ORDER BY userId, start\n";
            reportText.appendText("Appointments by Consultant\n");
        } else if (actionEvent.getSource() == reportCButton) {
            reportText.clear();
            sql = "Third Option";
        } // end if

        try (Connection connection = DBConnection.dbConnect();
             Statement statement = connection.createStatement()){
            ResultSet results = statement.executeQuery(sql);
            if(actionEvent.getSource() == reportAButton) {
                while (results.next()) {
                    String line = results.getInt("Month") + "-" + results.getInt("Year") + " " + results.getString("Type") + " " + results.getInt("Total") + "\n";
                    reportText.appendText(line);
                } // end while
            } else if (actionEvent.getSource() == reportBButton) {
                int userId = 0;
                while (results.next()) {
                    if(results.getInt("userId") != userId) {
                        userId = results.getInt("userId");
                        reportText.appendText("Appointments for " + results.getString("contact") + "\n");
                    } // end if
                    String line = results.getTimestamp("start") + " - " + results.getString("type") + " with " + allCustomers.getCustomer(results.getInt("customerId")).getCustomerName() + "\n";
                    if(results.getTimestamp("start").toLocalDateTime().isAfter(LocalDateTime.now())) {
                        reportText.appendText(line);
                    } // end if
                } // end while
            } else if (actionEvent.getSource() == reportCButton) {

            } // end if
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    } // end runReportHandler

    public void backButtonHandler() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    } // end backButtonHandler
} // end ReportOutputScreen
