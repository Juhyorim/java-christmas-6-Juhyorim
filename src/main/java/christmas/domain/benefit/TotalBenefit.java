package christmas.domain.benefit;

import christmas.dto.BeneficialMenus;
import java.util.Map;

public class TotalBenefit {
    private Map<BenefitType, Integer> discountPrice;
    private BeneficialMenus beneficialMenus;

    public TotalBenefit(Map<BenefitType, Integer> discountPrice, BeneficialMenus beneficialMenus) {
        this.discountPrice = discountPrice;
        this.beneficialMenus = beneficialMenus;
    }

    public Map<BenefitType, Integer> getDiscountPrice() {
        return discountPrice;
    }

    public BeneficialMenus getBeneficialMenus() {
        return beneficialMenus;
    }

    public int getTotalDiscountPrice() {
        return beneficialMenus.getDiscountedTotalPrice();
    }
}
