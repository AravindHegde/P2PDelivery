package model;

public class Driver {

    private String id;
    private String name;
    private volatile boolean available = true;

    private int completedOrders;
    private double rating;
    private int ratingCount;

    public Driver(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public synchronized void assignOrder() {
        available = false;
    }

    public synchronized void freeDriver() {
        available = true;
    }

    public synchronized void completeOrder() {
        completedOrders++;
    }

    public synchronized void addRating(double r) {
        rating = ((rating * ratingCount) + r) / (ratingCount + 1);
        ratingCount++;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getId() {
        return id;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }
}
