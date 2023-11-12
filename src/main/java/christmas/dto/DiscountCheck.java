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

    public static DiscountCheck make(Order orderForm) {
        return new DiscountCheck(orderForm.getTotalPrice(), orderForm.getOrderDate());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
