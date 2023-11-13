package christmas.dto;

import christmas.domain.benefit.discount.DiscountType;
import christmas.domain.benefit.discount.TotalDiscount;
import java.util.HashMap;
import java.util.Map;

public class BenefitDto {
    private Map<String, Integer> benefits = new HashMap<>();

    private BenefitDto() {
    }

    public static void convert(TotalBenefits totalBenefits) {
        BenefitDto benefitDto = new BenefitDto();

        TotalDiscount totalDiscount = totalBenefits.getTotalDiscount();
        Map<DiscountType, Integer> discountPrice = totalDiscount.getgetDiscountPriceByDiscountType();
        for (DiscountType discountType : discountPrice.keySet()) {
            benefitDto.benefits.put(discountType.getName(), discountPrice.get(discountType));
        }
    }
}
