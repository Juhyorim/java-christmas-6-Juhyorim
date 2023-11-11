package christmas.domain.benefit;

import christmas.domain.DiscountCalculator;
import christmas.domain.OrderForm;
import christmas.dto.BeneficialMenus;
import christmas.dto.BenefitCheckDto;
import java.time.LocalDate;
import java.util.List;

public class BenefitManager {
    public void getBenefits() {
        LocalDate date = LocalDate.of(2023, 12, 1);
        OrderForm orderForm = new OrderForm(date);

        BenefitCheckDto benefitCheckDto = BenefitCheckDto.make(orderForm);
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        BeneficialMenus beneficialMenus = DiscountCalculator.applyDiscount(orderForm, possibleBenefits);
        EventBadge eventBadge = EventBadge.getBadge(beneficialMenus.getDiscountedTotalPrice());
    }
}
