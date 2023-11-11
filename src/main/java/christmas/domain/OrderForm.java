package christmas.domain;

import java.time.LocalDate;

public class OrderForm {
    private final LocalDate orderDate;
    private int totalPrice;
    private OrderedMenu menus;

    public OrderForm(LocalDate orderDate) {
        this.orderDate = orderDate;
        this.totalPrice = 0;
        this.menus = new OrderedMenu();
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
}
