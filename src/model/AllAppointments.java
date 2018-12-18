package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public Appointment getAppointment(int appointmentId) {
        return allAppointments.get(appointmentId - 1);
    } // end getAppointment

    public ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    } // end getAllCustomers

    public void updateAppointment(Appointment appointment, int index) {
        allAppointments.set(index, appointment);
    } // end updateAppointment

} // end AllAppointments
