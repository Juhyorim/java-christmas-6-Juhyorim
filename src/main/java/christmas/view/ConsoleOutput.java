package christmas.view;

import christmas.domain.menu.Menu;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.util.MessageFormatter;
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
    private static StringBuilder messageBuffer = new StringBuilder();

    private static void initMessageBuffer() {
        messageBuffer.setLength(0);
    }

    public void greeting() {
        simplePrintMessage(OutputMessage.GREETING_MESSAGE.getMessage());
    }

    public void visitDayRequest() {
        simplePrintMessage(OutputMessage.VISIT_DAY_REQUEST.getMessage());
    }

    public void menuAndCountRequest() {
        simplePrintMessage(OutputMessage.MENU_AND_COUNT_REQUEST.getMessage());
    }

    public void printInvalidOrderError(String errorMessage) {
        if (errorMessage.equals(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED)) {
            simplePrintMessage(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED.getMessage());
            return;
        }

        simplePrintMessage(ErrorMessage.INVALID_ORDER.getMessage());
    }

    public void printInvalidDayError() {
        simplePrintMessage(ErrorMessage.INVALID_DAY.getMessage());
    }

    private void simplePrintMessage(String message) {
        initMessageBuffer();
        messageBuffer.append(message + NEW_LINE);

        printMessage(messageBuffer);
    }

    public void printResultStart() {
        simplePrintMessage(OutputMessage.PRINT_RESULT_START_MESSAGE.getMessage() + NEW_LINE);
    }

    public void printOrderMenu(Order orderForm) {
        String title = TitleMessage.ORDER_MENU.getMessage();
        StringBuilder orderMenuContents = new StringBuilder();

        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            orderMenuContents.append(menu.getName() + " " + orderedMenu.getCount(menu) + NUMBER_UNIT + NEW_LINE);
        }

        printWithTitle(title, orderMenuContents.toString().trim());
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        String title = TitleMessage.TOTAL_PRICE_BEFORE_DISCOUNT.getMessage();
        String content = MessageFormatter.getFormattedPrice(totalPrice) + WON;
        printWithTitle(title, content);
    }

    public void printGift(List<GiftProduct> giftProducts) {
        String title = TitleMessage.GIFT.getMessage();
        String contents = giftProductsContents(giftProducts);

        printWithTitle(title, contents);
    }

    private String giftProductsContents(List<GiftProduct> giftProducts) {
        if (giftProducts.size() == 0) {
            return (NONE + NEW_LINE);
        }

        StringBuilder giftProductsContents = new StringBuilder();
        for (GiftProduct giftProduct : giftProducts) {
            giftProductsContents.append(giftProduct.getName() + " " + "1" + NUMBER_UNIT + NEW_LINE);
        }

        return giftProductsContents.toString().trim();
    }

    public void printBenefits(Map<String, Integer> benefits) {
        String title = TitleMessage.BENEFIT.getMessage();
        String benefitContents = getBenefitContents(benefits);
        printWithTitle(title, benefitContents);
    }

    private String getBenefitContents(Map<String, Integer> benefits) {
        if (benefits.size() == 0) {
            return NONE;
        }

        StringBuilder benefitContents = new StringBuilder();

        for (String benefitName : benefits.keySet()) {
            benefitContents.append(
                    benefitName + ": " + "-" + MessageFormatter.getFormattedPrice(benefits.get(benefitName)) + WON
                            + NEW_LINE);
        }

        return benefitContents.toString().trim();
    }

    public void printTotalDiscount(int totalDiscountPrice) {
        String title = TitleMessage.TOTAL_BENEFIT_PRICE.getMessage();
        String content = MessageFormatter.getFormattedPrice(totalDiscountPrice) + WON;
        printWithTitle(title, content);
    }

    public void printActualPaymentAmount(int actualPaymentAmount) {
        String title = TitleMessage.ACTUAL_PAYMENT_AMOUNT.getMessage();
        String content = MessageFormatter.getFormattedPrice(actualPaymentAmount) + WON;
        printWithTitle(title, content);
    }

    public void printEventBadge(String eventBadgeName) {
        String title = TitleMessage.EVENT_BADGE.getMessage();
        String content = eventBadgeName;
        printWithTitle(title, content);
    }

    private void printWithTitle(String title, String content) {
        initMessageBuffer();
        messageBuffer.append(title + NEW_LINE);
        messageBuffer.append(content + NEW_LINE);
        messageBuffer.append(NEW_LINE);

        printMessage(messageBuffer);
    }

    private void printMessage(StringBuilder messageBuffer) {
        System.out.print(messageBuffer);
    }
}
