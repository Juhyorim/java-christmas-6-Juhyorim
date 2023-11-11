package christmas.domain.benefit;

import christmas.domain.OrderForm;
import christmas.dto.BeneficialMenus;

public interface BenefitApplier {
    BeneficialMenus apply(OrderForm orderForm);
}
