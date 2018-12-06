package model;

import java.time.ZonedDateTime;

public class Appointment {
    private int Id;
    private int customerId;
    private int consultantId;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public Appointment() {} // end default constructor

    public Appointment(int customerId, int consultantId, String title, String description, String location, String type, ZonedDateTime startTime, int apptLength) {
        this.customerId = customerId;
        this.consultantId = consultantId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(apptLength);
    } // end constructor

} // end Appointment
