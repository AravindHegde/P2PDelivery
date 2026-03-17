package model;

import java.time.LocalDateTime;

public class Order {

    private String id;
    private Customer customer;
    private Item item;

    private volatile OrderStatus status;
    private Driver assignedDriver;

    private LocalDateTime createdTime;

    public Order(String id, Customer customer, Item item) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.status = OrderStatus.CREATED;
        this.createdTime = LocalDateTime.now();
    }

    public synchronized void assignDriver(Driver driver) {
        this.assignedDriver = driver;
        this.status = OrderStatus.ASSIGNED;
    }

    public synchronized void pickup() {
        status = OrderStatus.PICKED_UP;
    }

    public synchronized void deliver() {
        status = OrderStatus.DELIVERED;
    }

    public synchronized void cancel() {

        if (status == OrderStatus.PICKED_UP || status == OrderStatus.DELIVERED) {
            throw new RuntimeException("Order already picked up. Cannot cancel.");
        }

        if (status == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled.");
        }

        status = OrderStatus.CANCELLED;
    }

    public String getId() {
        return id;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
