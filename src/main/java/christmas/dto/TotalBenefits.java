package christmas.dto;

import christmas.domain.benefit.discount.DiscountType;
import christmas.domain.benefit.EventBadge;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.domain.benefit.discount.TotalDiscount;
import christmas.domain.benefit.gift.PossibleGift;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalBenefits {
    private int totalBenefitPrice = 0;
    private int discountPrice = 0;
    private int giftPrice = 0;
    private TotalDiscount totalDiscount;
    private PossibleGift gifts;
    private EventBadge eventBadge;

    public TotalBenefits(TotalDiscount totalDiscount, PossibleGift gifts) {
        this.totalDiscount = totalDiscount;
        this.gifts = gifts;
        updateTotalDiscountPrice(totalDiscount, gifts);
    }

    public int getActualDiscountPrice(int totalPrice) {
        int actualPrice = totalPrice - totalDiscount.getTotalDiscountPrice();
        if (totalPrice < 0) {
            return 0;
        }

        return actualPrice;
    }

    private void updateTotalDiscountPrice(TotalDiscount totalDiscounts, PossibleGift gifts) {
        discountPrice = totalDiscounts.getTotalDiscountPrice();
        giftPrice = gifts.getTotalPrice();
        totalBenefitPrice = discountPrice + giftPrice;
    }

    public TotalDiscount getTotalDiscount() {
        return totalDiscount;
    }

    public PossibleGift getGifts() {
        return gifts;
    }

    public void addEventBadge(EventBadge eventBadge) {
        this.eventBadge = eventBadge;
    }

    public EventBadge getEventBadge() {
        return eventBadge;
    }

    public int getTotalBenefitPrice() {
        return totalBenefitPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public Map<String, Integer> getBenefits() {
        Map<String, Integer> benefits = new HashMap<>();

        Map<DiscountType, Integer> benefitByDiscountType = totalDiscount.getgetDiscountPriceByDiscountType();
        for (DiscountType discountType : benefitByDiscountType.keySet()) {
            benefits.put(discountType.getName(), benefitByDiscountType.get(discountType));
        }

        List<GiftProduct> giftProducts = gifts.getGiftProducts();
        for (GiftProduct giftProduct : giftProducts) {
            benefits.put("증정이벤트", benefits.getOrDefault(giftProduct, 0) + giftProduct.getPrice());
        }

        return benefits;
    }
}
