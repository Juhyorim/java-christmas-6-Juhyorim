package christmas.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderForm {
    private final LocalDate orderDate;
    private int totalPrice;
    private Map<Menu, Integer> menus;

    public OrderForm(LocalDate orderDate) {
        this.orderDate = orderDate;
        this.totalPrice = 0;
        this.menus = new HashMap<>();
    }

    public void addMenu(Menu menu, int count) {
        menus.put(menu, menus.getOrDefault(menu, 0) + count);
        totalPrice += (menu.getPrice()) * count;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
