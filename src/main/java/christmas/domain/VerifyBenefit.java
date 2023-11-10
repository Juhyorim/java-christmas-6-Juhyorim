package christmas.domain;

import christmas.dto.BenefitCheckDto;

public interface VerifyBenefit {
    boolean check(BenefitCheckDto benefitCheckDto);
}
