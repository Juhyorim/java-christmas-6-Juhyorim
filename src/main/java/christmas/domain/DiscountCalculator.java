package christmas.domain;

import christmas.dto.DiscountedMenu;
import java.util.List;

public class DiscountCalculator {
    public static DiscountedMenu applyDiscount(OrderForm orderForm, List<BenefitType> benefits) {
        DiscountedMenu discountedMenu = new DiscountedMenu();

        for (BenefitType benefitType : benefits) {
            discountedMenu.add(BenefitType.apply(benefitType, orderForm));
        }

        return discountedMenu;
    }
}
