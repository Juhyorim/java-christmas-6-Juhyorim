package christmas.domain.benefit;

import christmas.EventDayValidator;
import christmas.domain.Menu;
import christmas.domain.OrderForm;
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

    public void startOrder() {
        consoleOutput.greeting();
        int orderDayOfMonth = getOrderDayOfMonth();
        OrderForm orderForm = getValidOrderForm(orderDayOfMonth);
    }

    private OrderForm getValidOrderForm(int orderDayOfMonth) {
        Map<Menu, Integer> orders = getOrders();
        OrderForm orderForm = OrderForm.make(LocalDate.of(2023, 12, orderDayOfMonth), orders);

        return orderForm;
    }

    private Map<Menu, Integer> getOrders() {
        while (true) {
            try {
                consoleOutput.menuAndCountRequest();
                String input = consoleInput.getMenuAndCount();
                return OrderInputManager.getValidOrder(input);
            } catch (IllegalArgumentException e) {
                consoleOutput.printInvalidDayError();
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
