package christmas.domain;

import christmas.domain.benefit.BenefitType;
import christmas.dto.BeneficialMenus;
import java.util.List;

public class DiscountCalculator {
    public static BeneficialMenus applyDiscount(OrderForm orderForm, List<BenefitType> benefits) {
        BeneficialMenus discountedMenu = new BeneficialMenus();

        for (BenefitType benefitType : benefits) {
            discountedMenu.add(BenefitType.apply(benefitType, orderForm));
        }

        return discountedMenu;
    }
}
