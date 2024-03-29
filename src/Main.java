import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

//        Locale.setDefault(new Locale("es")); // Uncomment to change Default Locale for language testing.
        System.out.println("Your current system ZoneId is: " + ZoneId.systemDefault());
        ResourceBundle rb = ResourceBundle.getBundle("languagefiles.Login", Locale.getDefault());

        Parent root = FXMLLoader.load(getClass().getResource("viewcontroller/LoginScreen.fxml"));
        primaryStage.setTitle(String.valueOf(rb.getObject("loginTitle")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    } // end start

    public static void main(String[] args) {
        launch(args);
    }
} // end Main