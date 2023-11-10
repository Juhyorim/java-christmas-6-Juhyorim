package christmas.domain;

import java.time.LocalDate;
import java.util.List;

public class BenefitManager {
    public void getBenefits() {
        int totalPrice = 1000;
        LocalDate date = LocalDate.of(2023, 12, 1);

        List<BenefitType> benefits = BenefitType.getPossibleBenefits(totalPrice, date);
    }
}
