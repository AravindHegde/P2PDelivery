package service;

import model.Driver;
import repository.DataStore;
import strategy.DriverRankingStrategy;

import java.util.ArrayList;
import java.util.List;

public class DriverDashboardService {

    public List<Driver> getTopDrivers(DriverRankingStrategy strategy) {

        List<Driver> drivers = new ArrayList<>(DataStore.drivers.values());

        return strategy.rank(drivers);
    }
}