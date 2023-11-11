package christmas.dto;

import christmas.domain.OrderForm;
import java.time.LocalDate;

public class BenefitCheckDto {
    private int totalPrice;
    private LocalDate date;

    public BenefitCheckDto(int totalPrice, LocalDate date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public static BenefitCheckDto make(OrderForm orderForm) {
        return new BenefitCheckDto(orderForm.getTotalPrice(), orderForm.getOrderDate());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
