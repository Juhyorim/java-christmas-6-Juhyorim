package christmas.domain.benefit;

import christmas.dto.BenefitCheckDto;

public interface BenefitVerification {
    boolean check(BenefitCheckDto benefitCheckDto);
}
