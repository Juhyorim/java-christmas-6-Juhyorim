package christmas.domain.benefit.discount;

import christmas.domain.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountCalculator {
    private DiscountCalculator() {
    }

    public static TotalDiscount applyDiscount(Order order, List<DiscountType> discounts) {
        DiscountedMenus discountedMenu = new DiscountedMenus();
        Map<DiscountType, Integer> discountPriceByBenefit = new HashMap<>();

        for (DiscountType discountType : discounts) {
            DiscountedMenus apply = DiscountType.apply(discountType, order);
            discountedMenu.add(apply);
            discountPriceByBenefit.put(discountType, apply.getDiscountedTotalPrice());
        }

        return new TotalDiscount(discountPriceByBenefit, discountedMenu);
    }
}
