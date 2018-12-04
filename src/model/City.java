package model;

public class City {
    private int id;
    private String name;
    private int countryId;

    public City(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    } // end Constructors

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
    } // end setname

    public int getCountryId() {
        return countryId;
    } // end getCountryId

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    } // end setCountryId
} // end City
