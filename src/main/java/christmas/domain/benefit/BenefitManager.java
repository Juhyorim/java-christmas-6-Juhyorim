package christmas.domain.benefit;

import christmas.domain.benefit.discount.DiscountCalculator;
import christmas.domain.Order;
import christmas.domain.benefit.discount.DiscountType;
import christmas.domain.benefit.discount.TotalDiscount;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.domain.benefit.gift.PossibleGift;
import christmas.dto.DiscountCheck;
import christmas.dto.TotalBenefits;
import christmas.dto.GiftCheck;
import java.util.List;

public class BenefitManager {
    public TotalBenefits getBenefits(Order order) {
        TotalDiscount totalDiscount = getDiscountBenefit(order);
        PossibleGift possibleGift = getGift(order.getTotalPrice());

        TotalBenefits totalBenefits = new TotalBenefits(totalDiscount, possibleGift);

        EventBadge eventBadge = EventBadge.getBadge(totalBenefits.getTotalBenefitPrice());
        totalBenefits.addEventBadge(eventBadge);

        return totalBenefits;
    }

    private PossibleGift getGift(int getDiscountedTotalPrice) {
        GiftCheck giftCheckDto = new GiftCheck(getDiscountedTotalPrice);
        return GiftProduct.getPossibleGift(giftCheckDto);
    }

    private static TotalDiscount getDiscountBenefit(Order order) {
        DiscountCheck benefitCheckDto = DiscountCheck.make(order);
        List<DiscountType> possibleDiscount = DiscountType.getPossibleDiscount(benefitCheckDto);

        TotalDiscount totalBenefit = DiscountCalculator.applyDiscount(order, possibleDiscount);
        return totalBenefit;
    }
}