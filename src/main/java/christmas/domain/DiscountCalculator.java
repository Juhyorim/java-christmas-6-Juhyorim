package christmas.domain;

import christmas.domain.benefit.BenefitType;
import christmas.domain.benefit.TotalBenefit;
import christmas.dto.BeneficialMenus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountCalculator {
    public static TotalBenefit applyDiscount(OrderForm orderForm, List<BenefitType> benefits) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        Map<BenefitType, Integer> discountPriceByBenefit = new HashMap<>();

        for (BenefitType benefitType : benefits) {
            BeneficialMenus apply = BenefitType.apply(benefitType, orderForm);
            discountedMenu.add(BenefitType.apply(benefitType, orderForm));
            discountPriceByBenefit.put(benefitType, apply.getDiscountedTotalPrice());
        }

        return new TotalBenefit(discountPriceByBenefit, discountedMenu);
    }
}
