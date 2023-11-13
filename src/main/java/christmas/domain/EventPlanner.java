package christmas.domain;

import christmas.EventDayValidator;
import christmas.domain.benefit.BenefitManager;
import christmas.domain.menu.Menu;
import christmas.dto.TotalBenefits;
import christmas.util.OrderInputManager;
import christmas.view.ConsoleInput;
import christmas.view.ConsoleOutput;
import christmas.view.InputValidator;
import christmas.view.Parser;
import java.time.LocalDate;
import java.util.Map;

public class EventPlanner {
    private ConsoleInput consoleInput = new ConsoleInput();
    private ConsoleOutput consoleOutput = new ConsoleOutput();
    private BenefitManager benefitManager = new BenefitManager();

    public void startOrder() {
        consoleOutput.greeting();
        int orderDayOfMonth = getOrderDayOfMonth();
        Order orderForm = getValidOrderForm(orderDayOfMonth);
        TotalBenefits benefits = benefitManager.getBenefits(orderForm);

        printResult(orderForm, benefits);
    }

    private void printResult(Order orderForm, TotalBenefits benefits) {
        consoleOutput.printResultStart();
        consoleOutput.printOrderMenu(orderForm);
        consoleOutput.printTotalPriceBeforeDiscount(orderForm.getTotalPrice());

        consoleOutput.printGift(benefits.getGifts().getGiftProducts());
        consoleOutput.printBenefits(benefits.getBenefits());
        consoleOutput.printTotalDiscount(benefits.getTotalBenefitPrice());
        consoleOutput.printActualPaymentAmount(benefits.getActualDiscountPrice(orderForm.getTotalPrice()));
        consoleOutput.printEventBadge(benefits.getEventBadge().getName());
    }

    private Order getValidOrderForm(int orderDayOfMonth) {
        Map<Menu, Integer> orders = getOrders();
        Order orderForm = Order.make(LocalDate.of(2023, 12, orderDayOfMonth), orders);

        return orderForm;
    }

    private Map<Menu, Integer> getOrders() {
        while (true) {
            try {
                consoleOutput.menuAndCountRequest();
                String input = consoleInput.getMenuAndCount();
                return OrderInputManager.getValidOrder(input);
            } catch (IllegalArgumentException e) {
                consoleOutput.printInvalidOrderError();
            }
        }
    }

    private int getOrderDayOfMonth() {
        return numberRepeatedInput((input) -> EventDayValidator.validate(input));
    }

    private int numberRepeatedInput(InputValidator inputValidator) {
        return (int) repeatedInput(inputValidator, (input) -> Integer.parseInt(input));
    }

    private Object repeatedInput(InputValidator inputValidator, Parser parser) {
        while (true) {
            try {
                consoleOutput.visitDayRequest();
                String input = consoleInput.getVisitDayOfMonth();
                inputValidator.validate(input);
                return parser.parse(input);
            } catch (IllegalArgumentException e) {
                consoleOutput.printInvalidDayError();
            }
        }
    }
}
