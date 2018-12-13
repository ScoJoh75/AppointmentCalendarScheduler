package model;

import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;
import static viewcontroller.MainMenu.allCustomers;

public class Appointment {
    private int Id;
    private int customerId;
    private String customerName;
    private int consultantId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String appointmentLength;

    public Appointment() {} // end default constructor

    public Appointment(int Id, int consultantId, String title, String description, String location, String contact, String type, ZonedDateTime startTime, String appointmentLength) {
        this.customerId = Id;
        this.consultantId = consultantId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(Integer.valueOf(appointmentLength));
        this.appointmentLength = appointmentLength;
        setCustomerName();
    } // end pre-database constructor

    public Appointment(int Id, int customerId, int consultantId, String title, String description, String location, String contact, String type, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.Id = Id;
        this.customerId = customerId;
        this.consultantId = consultantId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentLength = String.valueOf(MINUTES.between(startTime.toLocalTime(), endTime.toLocalTime()));
        setCustomerName();
    } // end full constructor

    public int getId() {
        return Id;
    } // end getId

    public void setId(int id) {
        Id = id;
    } // end setId

    public int getCustomerId() {
        return customerId;
    } // end getCustomerId

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    } // end setCustomerId

    public int getConsultantId() {
        return consultantId;
    } // end getConsultantId

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
    } // end setConsultantId

    public String getTitle() {
        return title;
    } // end getTitle

    public void setTitle(String title) {
        this.title = title;
    } // end setTitle

    public String getDescription() {
        return description;
    } // end getDescription

    public void setDescription(String description) {
        this.description = description;
    } // end setDescription

    public String getLocation() {
        return location;
    } // end getLocation

    public void setLocation(String location) {
        this.location = location;
    } // end setLocation

    public String getContact() {
        return contact;
    } // end getContact

    public void setContact(String contact) {
        this.contact = contact;
    } // end setContact;

    public String getType() {
        return type;
    } // end getType

    public void setType(String type) {
        this.type = type;
    } // end setType

    public ZonedDateTime getStartTime() {
        return startTime;
    } // end getStartTime

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    } // end setStartTime

    public ZonedDateTime getEndTime() {
        return endTime;
    } // end getEndTime

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    } // end setEndTime

    public String getAppointmentLength() {
        return appointmentLength;
    } // end getAppointmentLength

    public void setAppointmentLength(String appointmentLength) {
        this.appointmentLength = appointmentLength;
    } // end setAppointmentLength

    public void setCustomerName() {
        this.customerName = allCustomers.getCustomer(customerId).getCustomerName();
    } // end getCustomerName

    public String getCustomerName() { return customerName;} // end getCustomerName
} // end Appointment
