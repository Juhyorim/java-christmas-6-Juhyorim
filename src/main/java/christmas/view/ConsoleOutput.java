package christmas.view;

import christmas.domain.menu.Menu;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.view.message.ErrorMessage;
import christmas.view.message.OutputMessage;
import christmas.view.message.TitleMessage;
import java.util.List;
import java.util.Map;

public class ConsoleOutput {
    public static final String WON = "원";
    public static final String NONE = "없음";
    public static final String NEW_LINE = "\n";
    public static final String NUMBER_UNIT = "개";

    public void greeting() {
        System.out.println(OutputMessage.GREETING_MESSAGE.getMessage());
    }

    public void visitDayRequest() {
        System.out.println(OutputMessage.VISIT_DAY_REQUEST.getMessage());
    }

    public void menuAndCountRequest() {
        System.out.println(OutputMessage.MENU_AND_COUNT_REQUEST.getMessage());
    }

    public void printInvalidOrderError(String errorMessage) {
        if (errorMessage.equals(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED)) {
            System.out.println(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED.getMessage());
            return;
        }

        System.out.println(ErrorMessage.INVALID_ORDER.getMessage());
    }

    public void printInvalidDayError() {
        System.out.println(ErrorMessage.INVALID_DAY.getMessage());
    }

    public void printResultStart() {
        System.out.println(OutputMessage.PRINT_RESULT_START_MESSAGE.getMessage() + NEW_LINE);
    }

    public void printOrderMenu(Order orderForm) {
        System.out.println(TitleMessage.ORDER_MENU.getMessage());
        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            System.out.println(menu.getName() + " " + orderedMenu.getCount(menu) + NUMBER_UNIT);
        }
        System.out.println();
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        System.out.println(TitleMessage.TOTAL_PRICE_BEFORE_DISCOUNT.getMessage());
        System.out.println("" + totalPrice + WON + NEW_LINE);
    }

    public void printGift(List<GiftProduct> giftProducts) {
        System.out.println(TitleMessage.GIFT.getMessage());
        if (giftProducts.size() == 0) {
            System.out.println(NONE + NEW_LINE);
            return;
        }

        for (GiftProduct giftProduct : giftProducts) {
            System.out.println(giftProduct.getName() + " " + "1" + NUMBER_UNIT);
        }

        System.out.println();
    }

    public void printBenefits(Map<String, Integer> benefits) {
        System.out.println(TitleMessage.BENEFIT.getMessage());
        if (benefits.size() == 0) {
            System.out.println(NONE + NEW_LINE);
            return;
        }

        for (String benefitName : benefits.keySet()) {
            System.out.println(benefitName + ": " + "-" + benefits.get(benefitName) + WON);
        }

        System.out.println();
    }

    public void printTotalDiscount(int totalDiscountPrice) {
        System.out.println(TitleMessage.TOTAL_BENEFIT_PRICE.getMessage());
        System.out.println(totalDiscountPrice + WON + NEW_LINE);
    }

    public void printActualPaymentAmount(int actualPaymentAmount) {
        System.out.println(TitleMessage.ACTUAL_PAYMENT_AMOUNT.getMessage());
        System.out.println(actualPaymentAmount + WON + NEW_LINE);
    }

    public void printEventBadge(String eventBadgeName) {
        System.out.println(TitleMessage.EVENT_BADGE.getMessage());
        System.out.println(eventBadgeName);
    }
}
