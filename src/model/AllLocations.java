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

    public ObservableList<City> getSelectedCities (int countryId) {
        ObservableList<City> selectedCities = FXCollections.observableArrayList();
        for(City city : allCities) {
            if(city.getCountryId() == countryId) {
                selectedCities.add(city);
            } // end if
        } // end for
        return selectedCities;
    } // end getSelectedCity

    public void addCountry(Country country) {
        this.allCountries.add(country);
    } // end addCountry

    public ObservableList<Country> getAllCountries() {
        return allCountries;
    } // end getAllCountries
} // end AllLocations
