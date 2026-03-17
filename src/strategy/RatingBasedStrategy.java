package strategy;

import model.Driver;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RatingBasedStrategy implements DriverRankingStrategy {

    @Override
    public List<Driver> rank(List<Driver> drivers) {

        return drivers.stream()
                .sorted(Comparator.comparingDouble(Driver::getRating).reversed())
                .collect(Collectors.toList());

    }
}