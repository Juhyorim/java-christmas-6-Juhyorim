package christmas.domain.benefit;

import christmas.domain.DiscountCalculator;
import christmas.domain.Order;
import christmas.domain.benefit.discount.DiscountType;
import christmas.domain.benefit.discount.TotalDiscount;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.domain.benefit.gift.PossibleGift;
import christmas.domain.benefit.discount.DiscountCheck;
import christmas.dto.TotalBenefits;
import christmas.domain.benefit.gift.GiftCheck;
import java.util.List;

public class BenefitManager {
    public TotalBenefits getBenefits(Order orderForm) {
        TotalDiscount totalDiscount = getDiscountBenefit(orderForm);
        PossibleGift possibleGift = getGift(orderForm.getTotalPrice());

        TotalBenefits totalBenefits = new TotalBenefits(totalDiscount, possibleGift);

        EventBadge eventBadge = EventBadge.getBadge(totalBenefits.getTotalBenefitPrice());
        totalBenefits.addEventBadge(eventBadge);

        return totalBenefits;
    }

    private PossibleGift getGift(int getDiscountedTotalPrice) {
        GiftCheck giftCheckDto = new GiftCheck(getDiscountedTotalPrice);
        return GiftProduct.getPossibleGift(giftCheckDto);
    }

    private static TotalDiscount getDiscountBenefit(Order orderForm) {
        DiscountCheck benefitCheckDto = DiscountCheck.make(orderForm);
        List<DiscountType> possibleDiscount = DiscountType.getPossibleDiscount(benefitCheckDto);

        TotalDiscount totalBenefit = DiscountCalculator.applyDiscount(orderForm, possibleDiscount);
        return totalBenefit;
    }
}