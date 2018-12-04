package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AllLocations {

    private ObservableList<City> allCities = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public void addCity(City city) {
        this.allCities.add(city);
    } // end addCity

    public ObservableList<City> getAllCities() {
        return allCities;
    } // end getAllCities

    public void addCountry(Country country) {
        this.allCountries.add(country);
    } // end addCountry

    public ObservableList<Country> getAllCountries() {
        return allCountries;
    } // end getAllCountries
} // end AllLocations
