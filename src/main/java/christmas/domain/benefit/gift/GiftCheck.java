package christmas.domain.benefit.gift;

import christmas.domain.benefit.BenefitCheck;

public class GiftCheck implements BenefitCheck {
    private int discountedTotalPrice;

    public GiftCheck(int discountedTotalPrice) {
        this.discountedTotalPrice = discountedTotalPrice;
    }

    public int getDiscountedTotalPrice() {
        return discountedTotalPrice;
    }
}
