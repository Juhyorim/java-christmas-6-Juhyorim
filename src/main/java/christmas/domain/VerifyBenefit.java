package christmas.domain;

import java.time.LocalDate;

public interface VerifyBenefit {
    boolean check(int totalPrice, LocalDate date);
}
