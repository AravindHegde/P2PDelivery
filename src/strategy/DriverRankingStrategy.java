package strategy;

import model.Driver;

import java.util.List;

public interface DriverRankingStrategy {

    List<Driver> rank(List<Driver> drivers);

}