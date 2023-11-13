package christmas.util;

import java.text.DecimalFormat;

public class MessageFormatter {
    private MessageFormatter() {
    }

    public static String getFormattedPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(price);
    }
}
