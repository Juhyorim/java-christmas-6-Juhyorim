package christmas.domain.benefit.discount;

import christmas.dto.BeneficialMenus;
import java.util.Map;

public class TotalDiscount {
    private Map<DiscountType, Integer> discountPrice;
    private BeneficialMenus beneficialMenus;

    public TotalDiscount(Map<DiscountType, Integer> discountPrice, BeneficialMenus beneficialMenus) {
        this.discountPrice = discountPrice;
        this.beneficialMenus = beneficialMenus;
    }

    public Map<DiscountType, Integer> getDiscountPrice() {
        return discountPrice;
    }

    public BeneficialMenus getBeneficialMenus() {
        return beneficialMenus;
    }

    public int getTotalDiscountPrice() {
        return beneficialMenus.getDiscountedTotalPrice();
    }
}
