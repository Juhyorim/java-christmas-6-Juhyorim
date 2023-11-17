package christmas.domain.benefit;

import christmas.domain.Order;
import christmas.domain.benefit.discount.DiscountedMenus;

public interface BenefitApplier {
    DiscountedMenus apply(Order order);
}
