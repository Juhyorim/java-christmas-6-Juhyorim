package christmas.view;

import christmas.dto.GiftDto;
import christmas.dto.OrderMenuDto;
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


    public void printInvalidOrderError(String errorMessage) {
        if (errorMessage.equals(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED.getMessage())) {
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

    public void printResultStart(int dayOfMonth) {
        simplePrintMessage(
                OutputMessage.PRINT_RESULT_START_MESSAGE.getMessage().formatted(dayOfMonth) + LINE_SEPARATOR);
    }

    public void printOrderMenu(OrderMenuDto orderMenuDto) {
        String title = TitleMessage.ORDER_MENU.getMessage();
        StringBuilder orderMenuContents = new StringBuilder();

        for (String menuName : orderMenuDto.getKindOfMenu()) {
            String formatter = MessageFormat.MENU.getFormat();
            orderMenuContents.append(formatter.formatted(menuName, orderMenuDto.getCount(menuName)));
        }

        printWithTitle(title, orderMenuContents.toString().trim());
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        String title = TitleMessage.TOTAL_PRICE_BEFORE_DISCOUNT.getMessage();
        String formatter = MessageFormat.TOTAL_PRICE_BEFORE_DISCOUNT.getFormat();
        String content = formatter.formatted(MessageFormatter.getFormattedPrice(totalPrice));
        printWithTitle(title, content.trim());
    }

    public void printGift(GiftDto giftDto) {
        String title = TitleMessage.GIFT.getMessage();
        String contents = giftProductsContents(giftDto);

        printWithTitle(title, contents.trim());
    }

    private String giftProductsContents(GiftDto giftDto) {
        if (giftDto.getGiftCount() == 0) {
            return (NONE + LINE_SEPARATOR);
        }

        StringBuilder giftProductsContents = new StringBuilder();
        String formatter = MessageFormat.GIFT.getFormat();
        for (String giftProductName : giftDto.getGiftProductsTypes()) {
            giftProductsContents.append(
                    formatter.formatted(giftProductName, giftDto.getCount(giftProductName))
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
            String formattedPrice = MessageFormatter.getFormattedPrice(benefits.get(benefitName));
            benefitContents.append(formatter.formatted(benefitName, formattedPrice));
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
