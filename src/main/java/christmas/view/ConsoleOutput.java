package christmas.view;

public class ConsoleOutput {
    public static final String GREETING_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    public static final String VISIT_DAY_REQUEST = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    public static final String MENU_AND_COUNT_REQUEST = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String INVALID_DAY = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";

    public void greeting() {
        System.out.println(GREETING_MESSAGE);
    }

    public void visitDayRequest() {
        System.out.println(VISIT_DAY_REQUEST);
    }

    public void menuAndCountRequest() {
        System.out.println(MENU_AND_COUNT_REQUEST);
    }

    public void printError(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }

    public void printInvalidDayError() {
        printError(INVALID_DAY);
    }
}