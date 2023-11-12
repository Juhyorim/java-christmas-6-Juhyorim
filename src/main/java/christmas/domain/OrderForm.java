package christmas.domain;

import java.time.LocalDate;
import java.util.Map;

public class OrderForm {
    private final LocalDate orderDate;
    private int totalPrice;
    private OrderedMenu menus;

    public OrderForm(LocalDate orderDate) {
        this.orderDate = orderDate;
        this.totalPrice = 0;
        this.menus = new OrderedMenu();
    }

    public static OrderForm make(LocalDate orderDate, Map<Menu, Integer> orders) {
        OrderForm orderForm = new OrderForm(orderDate);
        orderForm.addMenus(orders);

        return orderForm;
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
        }
    }
}
