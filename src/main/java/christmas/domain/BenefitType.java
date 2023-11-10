package christmas.domain;

import christmas.dto.BenefitCheckDto;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public enum BenefitType {
    CHRISTMAS_D_DAY((benefitCheckDto) -> isChristmasSeason(benefitCheckDto)),
    WEEK_DAY((benefitCheckDto) -> isWeekDay(benefitCheckDto)),
    WEEKEND((benefitCheckDto) -> isWeekend(benefitCheckDto)),
    SPECIAL((benefitCheckDto) -> isSpecialDay(benefitCheckDto)),
    GIFT((benefitCheckDto) -> canGetGift(benefitCheckDto));

    private static final int MINIMUM_AMOUNT = 10000;
    private static final int GIFT_WORTHY_PRICE = 120000;
    private static final List<Integer> SPECIAL_DAYS = List.of(3, 10, 17, 24, 25, 31);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2023, 12, 25);
    private static final LocalDate FIRST_DATE = LocalDate.of(2023, 12, 1);

    private VerifyBenefit verify;

    BenefitType(VerifyBenefit verify) {
        this.verify = verify;
    }

    private boolean canApply(BenefitCheckDto benefitCheckDto) {
        return verify.check(benefitCheckDto);
    }

    private static boolean isChristmasSeason(BenefitCheckDto benefitCheckDto) {
        if (isBetween(FIRST_DATE, CHRISTMAS_DATE, benefitCheckDto.getDate())) {
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

    private static boolean isWeekDay(BenefitCheckDto benefitCheckDto) {
        if (WeekManager.isWeekday(benefitCheckDto.getDate().getDayOfWeek())) {
            return true;
        }

        return false;
    }

    private static boolean isWeekend(BenefitCheckDto benefitCheckDto) {
        if (isWeekDay(benefitCheckDto)) {
            return false;
        }

        return true;
    }

    private static boolean isSpecialDay(BenefitCheckDto benefitCheckDto) {
        if (SPECIAL_DAYS.contains(benefitCheckDto.getDate().getDayOfMonth())) {
            return true;
        }

        return false;
    }

    private static boolean canGetGift(BenefitCheckDto benefitCheckDto) {
        if (benefitCheckDto.getTotalPrice() < GIFT_WORTHY_PRICE) {
            return false;
        }

        return true;
    }

    public static List<BenefitType> getPossibleBenefits(BenefitCheckDto benefitCheckDto) {
        List<BenefitType> benefits = new ArrayList<>();

        if (isUnderMinimumAmount(benefitCheckDto.getTotalPrice())) {
            return benefits;
        }

        if (is2023December(benefitCheckDto.getDate()) == false) {
            return benefits;
        }

        for (BenefitType benefitType : values()) {
            if (benefitType.canApply(benefitCheckDto)) {
                benefits.add(benefitType);
            }
        }

        return benefits;
    }

    private static boolean is2023December(LocalDate date) {
        if (date.getYear() == 2023 && date.getMonth() == Month.DECEMBER) {
            return true;
        }

        return false;
    }

    private static boolean isUnderMinimumAmount(int totalPrice) {
        return totalPrice < MINIMUM_AMOUNT;
    }
}