package christmas.domain.benefit;

import christmas.domain.OrderForm;
import christmas.dto.DiscountedMenu;

public interface BenefitApplier {
    DiscountedMenu apply(OrderForm orderForm);
}
