package model;

public class Country {
    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    } // end constructor

    public int getId() {
        return id;
    } // end getId

    public void setId(int id) {
        this.id = id;
    } // end setId

    public String getName() {
        return name;
    } // end getName

    public void setName(String name) {
        this.name = name;
    } // end setName
} // end Country
