package christmas.view;

import christmas.domain.benefit.gift.PossibleGift;
import christmas.domain.menu.Menu;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.benefit.gift.GiftProduct;
import christmas.util.MessageFormatter;
import christmas.view.message.ErrorMessage;
import christmas.view.message.MessageFormat;
import christmas.view.message.OutputMessage;
import christmas.view.message.TitleMessage;
import java.util.Map;

public class ConsoleOutput {
    public static final String NONE = "없음";
    private static final String LINE_SEPARATOR = System.lineSeparator();
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
        messageBuffer.append(message + LINE_SEPARATOR);

        printMessage(messageBuffer);
    }

    public void printResultStart() {
        simplePrintMessage(OutputMessage.PRINT_RESULT_START_MESSAGE.getMessage() + LINE_SEPARATOR);
    }

    public void printOrderMenu(Order order) {
        String title = TitleMessage.ORDER_MENU.getMessage();
        StringBuilder orderMenuContents = new StringBuilder();

        OrderedMenu orderedMenu = order.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            String formatter = MessageFormat.MENU.getFormat();
            orderMenuContents.append(formatter.formatted(menu.getName(), orderedMenu.getCount(menu)));
        }

        printWithTitle(title, orderMenuContents.toString().trim());
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        String title = TitleMessage.TOTAL_PRICE_BEFORE_DISCOUNT.getMessage();
        String formatter = MessageFormat.TOTAL_PRICE_BEFORE_DISCOUNT.getFormat();
        String content = formatter.formatted(MessageFormatter.getFormattedPrice(totalPrice));
        printWithTitle(title, content.trim());
    }

    public void printGift(PossibleGift giftProducts) {
        String title = TitleMessage.GIFT.getMessage();
        String contents = giftProductsContents(giftProducts);

        printWithTitle(title, contents.trim());
    }

    private String giftProductsContents(PossibleGift giftProducts) {
        if (giftProducts.getGiftCount() == 0) {
            return (NONE + LINE_SEPARATOR);
        }

        StringBuilder giftProductsContents = new StringBuilder();
        String formatter = MessageFormat.GIFT.getFormat();
        for (GiftProduct giftProduct : giftProducts.getGiftProductsTypes()) {
            giftProductsContents.append(
                    formatter.formatted(giftProduct.getName(), giftProducts.getCount(giftProduct))
            );
        }

        return giftProductsContents.toString().trim();
    }

    public void printBenefits(Map<String, Integer> benefits) {
        String title = TitleMessage.BENEFIT.getMessage();
        String benefitContents = getBenefitContents(benefits);
        printWithTitle(title, benefitContents.trim());
    }

    private String getBenefitContents(Map<String, Integer> benefits) {
        if (benefits.size() == 0) {
            return NONE;
        }

        StringBuilder benefitContents = new StringBuilder();
        String formatter = MessageFormat.BENEFIT.getFormat();

        for (String benefitName : benefits.keySet()) {
            benefitContents.append(
                    formatter.formatted(benefitName, MessageFormatter.getFormattedPrice(benefits.get(benefitName)))
            );
        }

        return benefitContents.toString().trim();
    }

    public void printTotalBenefitPrice(int totalDiscountPrice) {
        String title = TitleMessage.TOTAL_BENEFIT_PRICE.getMessage();
        String formatter = MessageFormat.TOTAL_BENEFIT_PRICE.getFormat();
        String content = formatter.formatted(MessageFormatter.getFormattedPrice(-totalDiscountPrice));
        printWithTitle(title, content.trim());
    }

    public void printActualPaymentAmount(int actualPaymentAmount) {
        String title = TitleMessage.ACTUAL_PAYMENT_AMOUNT.getMessage();
        String formatter = MessageFormat.ACTUAL_PAYMENT_AMOUNT.getFormat();
        String content = formatter.formatted(
                MessageFormatter.getFormattedPrice(actualPaymentAmount)
        );
        printWithTitle(title, content.trim());
    }

    public void printEventBadge(String eventBadgeName) {
        String title = TitleMessage.EVENT_BADGE.getMessage();
        String content = eventBadgeName;
        printWithTitle(title, content.trim());
    }

    private void printWithTitle(String title, String content) {
        initMessageBuffer();
        messageBuffer.append(title + LINE_SEPARATOR);
        messageBuffer.append(content + LINE_SEPARATOR);
        messageBuffer.append(LINE_SEPARATOR);

        printMessage(messageBuffer);
    }

    private void printMessage(StringBuilder messageBuffer) {
        System.out.print(messageBuffer);
    }
}
