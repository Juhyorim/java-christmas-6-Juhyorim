package christmas.dto;

import java.time.LocalDate;

public class BenefitCheckDto {
    private int totalPrice;
    private LocalDate date;

    public BenefitCheckDto(int totalPrice, LocalDate date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
