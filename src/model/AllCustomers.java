package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AllCustomers {

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public void addCustomer(Customer customer) {
        this.allCustomers.add(customer);
    } // end addCustomer

    public boolean removeCustomer(Customer customer) {
        boolean removed = false;
        if(allCustomers.remove(customer)) {
            removed = true;
        } // end if
        return removed;
    } // end removeCustomer

    public Customer getCustomer(int customerID) {
        for(Customer customer : allCustomers) {
            if(customerID == customer.getId()); {
                return allCustomers.get(customerID);
            } // end if
        } // end for
        return null;
    } // end getCustomer

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    } // end getAllCustomers

    public void updateCustomer(Customer customer, int index) {
        allCustomers.set(index, customer);
    } // end updateCustomer
} // end AllCustomers
