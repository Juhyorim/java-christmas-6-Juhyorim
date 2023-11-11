package christmas.domain.benefit;

import christmas.domain.Menu;
import christmas.domain.MenuGroup;
import christmas.domain.OrderForm;
import christmas.domain.OrderedMenu;
import christmas.util.WeekManager;
import christmas.dto.BenefitCheckDto;
import christmas.dto.BeneficialMenus;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public enum BenefitType {
    CHRISTMAS_D_DAY(
            (benefitCheckDto) -> isChristmasSeason(benefitCheckDto),
            (orderForm) -> applyChristmasDDayDiscount(orderForm)
    ),
    WEEK_DAY(
            (benefitCheckDto) -> isWeekDay(benefitCheckDto),
            (orderForm) -> applyWeekdayDiscount(orderForm)
    ),
    WEEKEND(
            (benefitCheckDto) -> isWeekend(benefitCheckDto),
            (orderForm) -> applyWeekendDiscount(orderForm)
    ),
    SPECIAL(
            (benefitCheckDto) -> isSpecialDay(benefitCheckDto),
            (orderForm) -> applySpecialDiscount()),
    GIFT((benefitCheckDto) -> canGetGift(benefitCheckDto), (orderForm) -> applyGiftBenefit());

    private static final int MINIMUM_AMOUNT = 10000;
    private static final int GIFT_WORTHY_PRICE = 120000;
    private static final List<Integer> SPECIAL_DAYS = List.of(3, 10, 17, 24, 25, 31);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2023, 12, 25);
    private static final LocalDate FIRST_DATE = LocalDate.of(2023, 12, 1);
    public static final int WEEK_DAY_DISCOUNT_PRICE = 2023;
    public static final int WEEKEND_DISCOUNT_PRICE = 2023;
    public static final int SPECIAL_DISCOUNT_PRICE = 1000;
    private BenefitVerification verify;
    private BenefitApplier benefitApplier;

    BenefitType(BenefitVerification verify, BenefitApplier benefitApplier) {
        this.verify = verify;
        this.benefitApplier = benefitApplier;
    }

    private static BeneficialMenus applyChristmasDDayDiscount(OrderForm orderForm) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        int discountTotalPrice = 1000 + (orderForm.getOrderDate().getDayOfMonth() - 1) * 100;
        discountedMenu.discountTotalPrice(discountTotalPrice);

        return discountedMenu;
    }

    private static BeneficialMenus applyWeekdayDiscount(OrderForm orderForm) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            if (MenuGroup.isInGroup(menu, MenuGroup.DESSERT)) {
                discountedMenu.discountPrice(menu, WEEK_DAY_DISCOUNT_PRICE, orderedMenu.getCount(menu));
            }
        }

        return discountedMenu;
    }

    private static BeneficialMenus applyWeekendDiscount(OrderForm orderForm) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            if (MenuGroup.isInGroup(menu, MenuGroup.MAIN)) {
                discountedMenu.discountPrice(menu, WEEKEND_DISCOUNT_PRICE, orderedMenu.getCount(menu));
            }
        }

        return discountedMenu;
    }

    private static BeneficialMenus applySpecialDiscount() {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        discountedMenu.discountTotalPrice(SPECIAL_DISCOUNT_PRICE);

        return discountedMenu;
    }

    private static BeneficialMenus applyGiftBenefit() {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        discountedMenu.addGiftProduct(GiftProduct.CHAMPAGNE);

        return discountedMenu;
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

    public static BeneficialMenus apply(BenefitType benefitType, OrderForm orderForm) {
        return benefitType.benefitApplier.apply(orderForm);
    }

    private boolean canApply(BenefitCheckDto benefitCheckDto) {
        return verify.check(benefitCheckDto);
    }
}