package christmas.view.message;

public enum TitleMessage {
    ORDER_MENU("<주문 메뉴>"),
    TOTAL_PRICE_BEFORE_DISCOUNT("<할인 전 총주문 금액>"),
    GIFT("<증정 메뉴>"),
    BENEFIT("<혜택 내역>"),
    TOTAL_BENEFIT_PRICE("<총혜택 금액>"),
    ACTUAL_PAYMENT_AMOUNT("<할인 후 예상 결제 금액>"),
    EVENT_BADGE("<12월 이벤트 배지>");
    String message;

    TitleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
