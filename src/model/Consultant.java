package model;

public class Consultant {
    private int Id;
    private String userName;

    public Consultant () {
        this.Id = 0;
        this.userName = "";
    } // end constructor

    // Getters & Setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

} // end Consultant Class
