package viewcontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;

import static viewcontroller.MainMenu.allAppointments;
import static viewcontroller.MainMenu.allCustomers;

public class AddModAppointmentScreen {
    private Appointment appointment;
    private boolean modifying;
    private int index;

    /**
     * setAppointment is called when a user decides to update an existing appointment.
     * The appointment selected in the appointment table will be passed into this method. Then
     * the values of the fields as they pertain to the appointment selected will be populated.
     * The 'modifying' variable which determines the behavior of the save button is set as well.
     * @param appointment the appointment selected in the appointment table when update was clicked.
     */
    void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        modifying = true;
//        addModLabel.setText("Update Appointment");
//        addModButton.setText("Update Appointment");
        // Lines below set the on screen fields with the data in the passed customer object..
//        customerNameField.setText(customer.getCustomerName());
//        address1Field.setText(customer.getAddress1());
//        address2Field.setText(customer.getAddress2());
//        cityField.setValue(allLocations.getAllCities().get(customer.getCityId()-1));
//        countryField.setValue(allLocations.getAllCountries().get(customer.getCountryId()-1));
//        postalCodeField.setText(customer.getPostalCode());
//        phoneField.setText(customer.getPhone());
        // determines the location in the AllCustomers array of the passed customer
        index = allAppointments.getAllAppointments().indexOf(appointment);
    } // end setCustomer

    /**
     * sceneChange is just a simple method that brings you back to the AppointmentScreen after Adding/Modding or just
     * plain cancelling out of the AddMod screen.
     */
    private void sceneChange() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addModButton.getScene().getWindow();
        stage.setTitle("Appointment Database");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end sceneChange
} // end AddModAppointmentScreen
