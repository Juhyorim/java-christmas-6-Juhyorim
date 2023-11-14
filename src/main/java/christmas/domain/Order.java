package christmas.domain;

import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.util.Map;

public class Order {
    private final LocalDate orderDate;
    private int totalPrice;
    private OrderedMenu menus;

    public Order(LocalDate orderDate) {
        this.orderDate = orderDate;
        this.totalPrice = 0;
        this.menus = new OrderedMenu();
    }

    public static Order make(LocalDate orderDate, Map<Menu, Integer> orders) {
        Order order = new Order(orderDate);
        order.addMenus(orders);

        return order;
    }

    public void addMenu(Menu menu, int count) {
        menus.add(menu, count);
        totalPrice += (menu.getPrice()) * count;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public OrderedMenu getMenus() {
        return menus;
    }

    public void addMenus(Map<Menu, Integer> orders) {
        for (Menu menu : orders.keySet()) {
            menus.add(menu, orders.get(menu));
            totalPrice += menu.getPrice() * orders.get(menu);
        }
    }

    public int getOrderDayOfMonth() {
        return orderDate.getDayOfMonth();
    }
}
