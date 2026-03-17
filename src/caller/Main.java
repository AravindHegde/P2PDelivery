package caller;

import model.Driver;
import model.Item;
import model.Order;
import model.OrderStatus;
import repository.DataStore;
import service.CustomerService;
import service.DriverDashboardService;
import service.DriverService;
import service.OrderService;
import strategy.OrderCountStrategy;
import strategy.RatingBasedStrategy;
import java.util.List;

public class Main {

    private static CustomerService customerService = new CustomerService();
    private static DriverService driverService = new DriverService();
    private static OrderService orderService = new OrderService(driverService);
    private static DriverDashboardService dashboardService = new DriverDashboardService();

    public static void main(String[] args) throws Exception {
        setupUsers();
        testFullOrderLifecycle();
        testCancellationFlow();
        testStatusApis();
        testDriverRankingStrategies();
        System.out.println("\n---- ALL TESTS COMPLETED ----");
    }

    private static void setupUsers() {
        System.out.println("\n---- SETUP CUSTOMERS ----");
        customerService.onboardCustomer("C1", "Aravind");
        customerService.onboardCustomer("C2", "Rahul");

        System.out.println("\n---- SETUP DRIVERS ----");
        driverService.onboardDriver("D1", "Ramesh");
        driverService.onboardDriver("D2", "Suresh");
    }

    private static void testFullOrderLifecycle() throws Exception {
        System.out.println("\n---- TEST ORDER LIFECYCLE ----");

        String orderId = orderService.createOrder("C1", Item.DOCUMENT);
        waitUntilAssigned(orderId);

        orderService.showOrderStatus(orderId);

        orderService.pickupOrder(orderId);
        orderService.deliverOrder(orderId);

        orderService.showOrderStatus(orderId);

        orderService.rateDriver(orderId, 4.5);
    }

    private static void testCancellationFlow() throws Exception {
        System.out.println("\n---- TEST CANCELLATION ----");

        String orderId = orderService.createOrder("C2", Item.FOOD);
        waitUntilAssigned(orderId);

        orderService.cancelOrder(orderId);

        orderService.showOrderStatus(orderId);
    }

    private static void testStatusApis() {
        System.out.println("\n---- DRIVER STATUS ----");
        driverService.showDriverStatus("D1");
        driverService.showDriverStatus("D2");
    }

    private static void testDriverRankingStrategies() {
        System.out.println("\n---- DRIVER DASHBOARD ----");

        List<Driver> topByOrders = dashboardService.getTopDrivers(new OrderCountStrategy());
        System.out.println("\nTop drivers by orders:");
        for (Driver d : topByOrders) {
            System.out.println(d.getName() + " -> Orders: " + d.getCompletedOrders());
        }

        List<Driver> topByRating = dashboardService.getTopDrivers(new RatingBasedStrategy());
        System.out.println("\nTop drivers by rating:");
        for (Driver d : topByRating) {
            System.out.println(d.getName() + " -> Rating: " + d.getRating());
        }
    }

    private static void waitUntilAssigned(String orderId) throws InterruptedException {
        while (true) {
            Order order = DataStore.orders.get(orderId);
            if (order.getStatus() == OrderStatus.ASSIGNED) {
                break;
            }
            Thread.sleep(200);
        }
    }
}