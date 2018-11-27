package viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DBConnection;

public class LoginScreen {

    @FXML
    public TextField loginUN;
    @FXML
    public PasswordField loginPW;

    @FXML
    public void processLogin() {
        System.out.println("I Clicked Submit!!!");
        System.out.println(loginUN.getText().toLowerCase());
        System.out.println(loginPW.getText());
    }
}
