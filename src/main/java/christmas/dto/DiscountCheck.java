package christmas.dto;

import christmas.domain.Order;
import java.time.LocalDate;

public class DiscountCheck implements BenefitCheck {
    private int totalPrice;
    private LocalDate date;

    public DiscountCheck(int totalPrice, LocalDate date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public static DiscountCheck make(Order order) {
        return new DiscountCheck(order.getTotalPrice(), order.getOrderDate());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
