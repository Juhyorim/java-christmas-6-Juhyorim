package christmas.domain;

import christmas.domain.benefit.discount.DiscountType;
import christmas.domain.benefit.discount.TotalDiscount;
import christmas.dto.DiscountedMenus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountCalculator {
    public static TotalDiscount applyDiscount(Order orderForm, List<DiscountType> discounts) {
        DiscountedMenus discountedMenu = new DiscountedMenus();
        Map<DiscountType, Integer> discountPriceByBenefit = new HashMap<>();

        for (DiscountType discountType : discounts) {
            DiscountedMenus apply = DiscountType.apply(discountType, orderForm);
            discountedMenu.add(apply);
            discountPriceByBenefit.put(discountType, apply.getDiscountedTotalPrice());
        }

        return new TotalDiscount(discountPriceByBenefit, discountedMenu);
    }
}
