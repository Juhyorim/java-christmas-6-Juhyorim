package christmas.domain.benefit;

import christmas.domain.Order;
import christmas.dto.BeneficialMenus;

public interface BenefitApplier {
    BeneficialMenus apply(Order orderForm);
}
