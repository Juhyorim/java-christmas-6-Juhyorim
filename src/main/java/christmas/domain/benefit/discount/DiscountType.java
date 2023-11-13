package christmas.domain.benefit.discount;

import christmas.domain.Menu;
import christmas.domain.MenuGroup;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.benefit.BenefitApplier;
import christmas.domain.benefit.BenefitVerification;
import christmas.dto.BenefitCheck;
import christmas.util.WeekManager;
import christmas.dto.DiscountCheck;
import christmas.dto.BeneficialMenus;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public enum DiscountType {
    CHRISTMAS_D_DAY(
            "크리스마스 디데이 할인",
            (benefitCheck) -> isChristmasSeason(benefitCheck),
            (orderForm) -> applyChristmasDDayDiscount(orderForm)
    ),
    WEEK_DAY(
            "평일 할인",
            (benefitCheck) -> isWeekDay(benefitCheck),
            (orderForm) -> applyWeekdayDiscount(orderForm)
    ),
    WEEKEND(
            "특별 할인",
            (benefitCheck) -> isWeekend(benefitCheck),
            (orderForm) -> applyWeekendDiscount(orderForm)
    ),
    SPECIAL(
            "특별 할인",
            (benefitCheck) -> isSpecialDay(benefitCheck),
            (orderForm) -> applySpecialDiscount());

    private static final int MINIMUM_AMOUNT = 10000;
    private static final List<Integer> SPECIAL_DAYS = List.of(3, 10, 17, 24, 25, 31);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2023, 12, 25);
    private static final LocalDate FIRST_DATE = LocalDate.of(2023, 12, 1);
    public static final int WEEK_DAY_DISCOUNT_PRICE = 2023;
    public static final int WEEKEND_DISCOUNT_PRICE = 2023;
    public static final int SPECIAL_DISCOUNT_PRICE = 1000;

    private String name;
    private BenefitVerification verify;
    private BenefitApplier benefitApplier;

    DiscountType(String name, BenefitVerification verify, BenefitApplier benefitApplier) {
        this.name = name;
        this.verify = verify;
        this.benefitApplier = benefitApplier;
    }

    private static BeneficialMenus applyChristmasDDayDiscount(Order orderForm) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        int discountTotalPrice = 1000 + (orderForm.getOrderDate().getDayOfMonth() - 1) * 100;
        discountedMenu.discountTotalPrice(discountTotalPrice);

        return discountedMenu;
    }

    private static BeneficialMenus applyWeekdayDiscount(Order orderForm) {
        BeneficialMenus discountedMenu = new BeneficialMenus();
        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            if (MenuGroup.isInGroup(menu, MenuGroup.DESSERT)) {
                discountedMenu.discountPrice(menu, WEEK_DAY_DISCOUNT_PRICE, orderedMenu.getCount(menu));
            }
        }

        return discountedMenu;
    }

    private static BeneficialMenus applyWeekendDiscount(Order orderForm) {
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

    private static boolean isChristmasSeason(BenefitCheck benefitCheck) {
        DiscountCheck discountCheck = (DiscountCheck) benefitCheck;
        if (isBetween(FIRST_DATE, CHRISTMAS_DATE, discountCheck.getDate())) {
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

    private static boolean isWeekDay(BenefitCheck benefitCheck) {
        DiscountCheck discountCheck = (DiscountCheck) benefitCheck;
        if (WeekManager.isWeekday(discountCheck.getDate().getDayOfWeek())) {
            return true;
        }

        return false;
    }

    private static boolean isWeekend(BenefitCheck benefitCheck) {
        DiscountCheck discountCheck = (DiscountCheck) benefitCheck;
        if (isWeekDay(discountCheck)) {
            return false;
        }

        return true;
    }

    private static boolean isSpecialDay(BenefitCheck benefitCheck) {
        DiscountCheck discountCheck = (DiscountCheck) benefitCheck;
        if (SPECIAL_DAYS.contains(discountCheck.getDate().getDayOfMonth())) {
            return true;
        }

        return false;
    }

    public static List<DiscountType> getPossibleDiscount(DiscountCheck benefitCheckDto) {
        List<DiscountType> benefits = new ArrayList<>();

        if (isUnderMinimumAmount(benefitCheckDto.getTotalPrice())) {
            return benefits;
        }

        if (is2023December(benefitCheckDto.getDate()) == false) {
            return benefits;
        }

        for (DiscountType benefitType : values()) {
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

    public static BeneficialMenus apply(DiscountType benefitType, Order orderForm) {
        return benefitType.benefitApplier.apply(orderForm);
    }

    private boolean canApply(DiscountCheck benefitCheckDto) {
        return verify.check(benefitCheckDto);
    }

    public String getName() {
        return name;
    }
}