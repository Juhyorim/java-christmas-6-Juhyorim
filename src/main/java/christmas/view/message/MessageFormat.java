package christmas.view.message;

public enum MessageFormat {
    MENU("%s %d개"),
    GIFT("%s %d개"),
    BENEFIT("%s: -%s원"),
    TOTAL_BENEFIT_PRICE("%s원"),
    ACTUAL_PAYMENT_AMOUNT("%s원"),
    TOTAL_PRICE_BEFORE_DISCOUNT("%s원");

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private String format;

    private  MessageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format + LINE_SEPARATOR;
    }
}
