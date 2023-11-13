package christmas.view;

public enum ErrorMessage {
    INVALID_DAY("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    ONLY_DRINK_ORDER_NOT_ALLOWED("음료만 주문이 불가능합니다"),
    INVALiD_MENU("해당하는 메뉴가 존재하지 않습니다."),
    INVALID_MENU_COUNT("메뉴 개수가 올바르지 않습니다."),
    UNDER_20_REQUIRED("메뉴 개수는 20개 이하여야합니다."),
    DUPLICATED_MENU_NOT_ALLOWED("메뉴는 중복되어선 안됩니다."),
    INVALID_INPUT_FORM("입력형식이 올바르지 않습니다");

    private static final String ERROR_PREFIX = "[ERROR] ";

    String name;

    ErrorMessage(String name) {
        this.name = name;
    }

    public String getMessage() {
        return ERROR_PREFIX + name;
    }
}
