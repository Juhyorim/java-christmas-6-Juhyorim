package christmas.domain;

import christmas.dto.DiscountedMenu;

public interface BenefitApplier {
    DiscountedMenu apply(OrderForm orderForm);
}
