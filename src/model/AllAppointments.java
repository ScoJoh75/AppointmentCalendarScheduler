package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class AllAppointments {
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) {
        this.allAppointments.add(appointment);
    } // end addAppointment

    public boolean removeAppointment(Appointment appointment) {
        boolean removed = false;
        if(allAppointments.remove(appointment)) {
            removed = true;
        } // end if
        return removed;
    } // end removeAppointment

    public ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    } // end getAllCustomers

    public void updateAppointment(Appointment appointment, int index) {
        allAppointments.set(index, appointment);
    } // end updateAppointment

    public boolean checkOverlap(Appointment appointment) {
        boolean overlap = false;

        int day = appointment.getStartTime().getDayOfMonth();
        int month = appointment.getStartTime().getMonthValue();
        int year = appointment.getStartTime().getYear();

        ArrayList<Appointment> sameDayAppointments = new ArrayList<>();
        StringBuilder overlapAlert = new StringBuilder();

        // Building temporary array list with appointments on the same day.
        for(Appointment sameDayAppointment : allAppointments) {
            if(!(appointment.getId() == sameDayAppointment.getId())) {
                int sDay = sameDayAppointment.getStartTime().getDayOfMonth();
                int sMonth = sameDayAppointment.getStartTime().getMonthValue();
                int sYear = sameDayAppointment.getStartTime().getYear();
                if (day == sDay && month == sMonth && year == sYear) {
                    sameDayAppointments.add(sameDayAppointment);
                } // end if
            } // end if
        } // end for

        // Checking if the array list is empty. If not, check start and end times for overlap
        if(!sameDayAppointments.isEmpty()) {
            // Appointments exist on the same day, check for overlap
            overlapAlert.append("Your appointment conflicts with the following appointment(s):\n");
            for(Appointment sameDayAppointment : sameDayAppointments) {
                String message = (sameDayAppointment.getLocalStartTime() + " that runs for " + sameDayAppointment.getAppointmentLength() + " minutes.\n");
                if(appointment.getStartTime().isAfter(sameDayAppointment.getStartTime()) && appointment.getStartTime().isBefore(sameDayAppointment.getEndTime())) {
                    overlap = true;
                    overlapAlert.append(message);
                } // end if
                if(appointment.getEndTime().isAfter(sameDayAppointment.getStartTime()) && appointment.getEndTime().isBefore(sameDayAppointment.getEndTime())) {
                    overlap = true;
                    overlapAlert.append(message);
                } // end if
                if(appointment.getStartTime().equals(sameDayAppointment.getStartTime())) {
                    overlap = true;
                    overlapAlert.append(message);
                } // end if
            } // end if

            if(overlap) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Scheduling Conflicts Detected!");
                alert.setHeaderText(null);
                alert.setContentText(overlapAlert.toString());
                alert.showAndWait();
            } // end if
        } // end if
        return overlap;
    } // end checkOverlap
} // end AllAppointments