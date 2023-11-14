package christmas.dto;

public class GiftCheck implements BenefitCheck {
    private int totalOrderPrice;

    public GiftCheck(int totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }
}
