package service;

import model.Driver;
import repository.DataStore;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DriverService {

    private BlockingQueue<Driver> availableDrivers = new LinkedBlockingQueue<>();

    public void onboardDriver(String id, String name) {
        Driver driver = new Driver(id, name);
        DataStore.drivers.put(id, driver);
        availableDrivers.offer(driver);
    }

    public Driver getAvailableDriver() throws InterruptedException {
        return availableDrivers.take();
    }

    public void markDriverAvailable(Driver driver) {
        driver.freeDriver();
        availableDrivers.offer(driver);
    }

    public void showDriverStatus(String driverId) {
        Driver driver = DataStore.drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found");
            return;
        }
        System.out.println("Driver " + driver.getName() + " available -> " + driver.isAvailable());
    }
}