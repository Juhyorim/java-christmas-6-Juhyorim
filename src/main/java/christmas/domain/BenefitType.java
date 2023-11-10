package christmas.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public enum BenefitType {
    CHRISTMAS_D_DAY((totalPrice, date) -> isChristmasSeason(date)),
    WEEK_DAY((totalPrice, date) -> isWeekDay(date)),
    WEEKEND((totalPrice, date) -> isWeekend(date)),
    SPECIAL((totalPrice, date) -> isSpecialDay()),
    GIFT((totalPrice, date) -> canGetGift());

    public static final int MINIMUM_AMOUNT = 10000;
    private VerifyBenefit verify;

    BenefitType(VerifyBenefit verify) {
        this.verify = verify;
    }

    private boolean canApply(int totalPrice, LocalDate date) {
        return verify.check(totalPrice, date);
    }

    private static boolean isChristmasSeason(LocalDate date) {
        LocalDate christmas = LocalDate.of(2023, 12, 25);
        LocalDate firstDay = LocalDate.of(2023, 12, 1);

        if (isBetween(firstDay, christmas, date)) {
            return true;
        }

        return false;
    }

    private static boolean isBetween(LocalDate firstDate, LocalDate lastDate, LocalDate date) {
        if ((date.isBefore(lastDate) || date.isEqual(lastDate)) == false) {
            return false;
        }

        if ((date.isAfter(firstDate) || date.isEqual(firstDate)) == false) {
            return false;
        }

        return true;
    }

    private static boolean isWeekDay(LocalDate date) {
        if (WeekManager.isWeekday(date.getDayOfWeek())) {
            return true;
        }

        return false;
    }

    private static boolean isWeekend(LocalDate date) {
        if (isWeekDay(date)) {
            return false;
        }

        return true;
    }

    private static boolean isSpecialDay() {
        return false;
    }

    private static boolean canGetGift() {
        return false;
    }

    public static List<BenefitType> getPossibleBenefits(int totalPrice, LocalDate date) {
        List<BenefitType> benefits = new ArrayList<>();

        if (isUnderMinimumAmount(totalPrice)) {
            return benefits;
        }

        for (BenefitType benefitType : values()) {
            if (benefitType.canApply(totalPrice, date)) {
                benefits.add(benefitType);
            }
        }

        return benefits;
    }

    private static boolean isUnderMinimumAmount(int totalPrice) {
        return totalPrice < MINIMUM_AMOUNT;
    }
}