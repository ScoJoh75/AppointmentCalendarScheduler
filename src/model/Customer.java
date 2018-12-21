package model;

import javafx.scene.control.Alert;

public class Customer {
    private int Id;
    private String customerName;
    private int active;
    private int addressId;
    private String address1;
    private String address2;
    private String postalCode;
    private String phone;
    private int cityId;
    private String cityName;
    private int countryId;
    private String countryName;

    public Customer() {} // end default Constructor

    public Customer(String customerName, String address1, String address2, String postalCode, String phone, String cityName, String countryName) {
        this.customerName = customerName;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.cityName = cityName;
        this.countryName = countryName;
    } // end short constructor

    public Customer(int Id, String customerName, int active, int addressId, String address1, String address2, String postalCode, String phone, int cityId, String cityName, int countryId, String countryName) {
        this.Id = Id;
        this.customerName = customerName;
        this.active = active;
        this.addressId = addressId;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryId = countryId;
        this.countryName = countryName;
    } // end full constructor

    // Getters & Setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean validate() {
        boolean valid = false;
        StringBuilder validalert = new StringBuilder();
        if(customerName.equals("")) validalert.append("Customer Name can not be blank.\n");
        if(address1.equals("")) validalert.append("Address Line 1 can not be blank.\n");
        if(postalCode.equals("")) validalert.append("Postal Code can not be blank.\n");
        if(phone.equals("")) validalert.append("Phone Number can not be blank.\n");
        if(cityId == 0) validalert.append("A City must be chosen.\n");
        if(countryId == 0) validalert.append("A country must be chosen.\n");

        if(validalert.length() != 0) {
            validalert.append("Please review and correct the problems listed.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("The customer can not be added!");
            alert.setHeaderText(null);
            alert.setContentText(validalert.toString());
            alert.showAndWait();
        } else {
            valid = true;
        } // end if

        return valid;
    } // end validate
} // end Customer Class
