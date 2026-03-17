package strategy;

import model.Driver;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCountStrategy implements DriverRankingStrategy {

    @Override
    public List<Driver> rank(List<Driver> drivers) {

        return drivers.stream()
                .sorted(Comparator.comparingInt(Driver::getCompletedOrders).reversed())
                .collect(Collectors.toList());
    }
}