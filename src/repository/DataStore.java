package repository;

import model.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

    public static Map<String, Customer> customers = new ConcurrentHashMap<>();
    public static Map<String, Driver> drivers = new ConcurrentHashMap<>();
    public static Map<String, Order> orders = new ConcurrentHashMap<>();

}
