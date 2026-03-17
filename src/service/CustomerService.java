package service;

import model.Customer;
import repository.DataStore;

public class CustomerService {

    public void onboardCustomer(String id, String name) {
        DataStore.customers.put(id, new Customer(id, name));
    }

}