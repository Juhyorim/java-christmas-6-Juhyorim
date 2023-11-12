package christmas.domain.benefit;

import christmas.domain.DiscountCalculator;
import christmas.domain.OrderForm;
import christmas.dto.BeneficialMenus;
import christmas.dto.BenefitCheckDto;
import christmas.dto.BenefitResult;
import java.util.List;

public class BenefitManager {
    public BenefitResult getBenefits(OrderForm orderForm) {
        BenefitCheckDto benefitCheckDto = BenefitCheckDto.make(orderForm);
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        BeneficialMenus beneficialMenus = DiscountCalculator.applyDiscount(orderForm, possibleBenefits);
        EventBadge eventBadge = EventBadge.getBadge(beneficialMenus.getDiscountedTotalPrice());

        return new BenefitResult(beneficialMenus, eventBadge);
    }
}
