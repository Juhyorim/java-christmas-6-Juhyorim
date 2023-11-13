package christmas.domain.benefit;

import christmas.domain.Order;
import christmas.dto.DiscountedMenus;

public interface BenefitApplier {
    DiscountedMenus apply(Order orderForm);
}
