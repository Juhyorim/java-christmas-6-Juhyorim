package christmas.view;

import christmas.domain.menu.Menu;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.benefit.gift.GiftProduct;
import java.util.List;
import java.util.Map;

public class ConsoleOutput {
    public static final String GREETING_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    public static final String VISIT_DAY_REQUEST = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    public static final String MENU_AND_COUNT_REQUEST = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    public static final String PRINT_RESULT_START_MESSAGE = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

    public void greeting() {
        System.out.println(GREETING_MESSAGE);
    }

    public void visitDayRequest() {
        System.out.println(VISIT_DAY_REQUEST);
    }

    public void menuAndCountRequest() {
        System.out.println(MENU_AND_COUNT_REQUEST);
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
        System.out.println(PRINT_RESULT_START_MESSAGE + "\n");
    }

    public void printOrderMenu(Order orderForm) {
        System.out.println("<주문 메뉴>");
        OrderedMenu orderedMenu = orderForm.getMenus();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            System.out.println("" + menu.getName() + " " + orderedMenu.getCount(menu) + "개");
        }
        System.out.println();
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println("" + totalPrice + "원\n");
    }

    public void printGift(List<GiftProduct> giftProducts) {
        System.out.println("<증정 메뉴>");
        if (giftProducts.size() == 0) {
            System.out.println("없음\n");
            return;
        }

        for (GiftProduct giftProduct : giftProducts) {
            System.out.println(giftProduct.getName() + " 1개");
        }

        System.out.println();
    }

    public void printBenefits(Map<String, Integer> benefits) {
        System.out.println("<혜택 내역>");
        if (benefits.size() == 0) {
            System.out.println("없음" + "\n");
            return;
        }

        for (String benefitName : benefits.keySet()) {
            System.out.println(benefitName + ": " + "-" + benefits.get(benefitName) + "원");
        }

        System.out.println();
    }

    public void printTotalDiscount(int totalDiscountPrice) {
        System.out.println("<총혜택 금액>");
        System.out.println(totalDiscountPrice + "원\n");
    }

    public void printActualPaymentAmount(int actualPaymentAmount) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(actualPaymentAmount + "원\n");
    }

    public void printEventBadge(String eventBadgeName) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(eventBadgeName);
    }
}
