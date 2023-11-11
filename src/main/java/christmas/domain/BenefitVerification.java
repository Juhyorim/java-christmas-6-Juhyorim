package christmas.domain;

import christmas.dto.BenefitCheckDto;

public interface BenefitVerification {
    boolean check(BenefitCheckDto benefitCheckDto);
}
