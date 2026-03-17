package service;

import model.*;
import notification.NotificationService;
import repository.DataStore;
import java.util.UUID;
import java.util.concurrent.*;

public class OrderService {

    private DriverService driverService;
    private NotificationService notifier = new NotificationService();
    private BlockingQueue<Order> pendingOrders = new LinkedBlockingQueue<>();
    private ExecutorService assignmentExecutor = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public OrderService(DriverService driverService) {
        this.driverService = driverService;
        assignmentExecutor.submit(() -> {
            while (true) {
                Order order = pendingOrders.take();
                if (order.getStatus() == OrderStatus.CANCELLED) {
                    continue;
                }
                Driver driver = driverService.getAvailableDriver();
                driver.assignOrder();
                order.assignDriver(driver);
                notifier.notifyUser("Order " + order.getId() + " assigned to driver " + driver.getName());
            }
        });
    }

    public String createOrder(String customerId, Item item) {
        Customer c = DataStore.customers.get(customerId);
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, c, item);
        DataStore.orders.put(orderId, order);
        pendingOrders.offer(order);
        scheduleAutoCancel(order);
        return orderId;
    }

    public void cancelOrder(String orderId) {
        Order order = DataStore.orders.get(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        order.cancel();
        Driver driver = order.getAssignedDriver();
        if (driver != null) {
            driverService.markDriverAvailable(driver);
        }
        notifier.notifyUser("Order " + orderId + " cancelled");
    }

    public void pickupOrder(String orderId) {
        Order order = DataStore.orders.get(orderId);
        if (order.getStatus() != OrderStatus.ASSIGNED) {
            throw new RuntimeException("Order not assigned yet");
        }
        order.pickup();
    }

    public void deliverOrder(String orderId) {
        Order order = DataStore.orders.get(orderId);
        if (order.getStatus() != OrderStatus.PICKED_UP) {
            throw new RuntimeException("Order must be picked up before delivery");
        }
        Driver driver = order.getAssignedDriver();
        order.deliver();
        driver.completeOrder();
        driverService.markDriverAvailable(driver);
        notifier.notifyUser("Order delivered");
    }

    public void showOrderStatus(String orderId) {
        Order order = DataStore.orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }
        System.out.println("Order " + orderId + " status -> " + order.getStatus());
    }

    public void rateDriver(String orderId, double rating) {
        Order order = DataStore.orders.get(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("Order not delivered yet");
        }
        Driver driver = order.getAssignedDriver();
        driver.addRating(rating);
        System.out.println("Driver rated successfully");
    }

    private void scheduleAutoCancel(Order order) {
        scheduler.schedule(() -> {
            if (order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.ASSIGNED) {
                order.cancel();
                Driver driver = order.getAssignedDriver();
                if (driver != null) {
                    driverService.markDriverAvailable(driver);
                }
                System.out.println("Auto cancelled order " + order.getId());
            }
        }, 30, TimeUnit.MINUTES);
    }
}