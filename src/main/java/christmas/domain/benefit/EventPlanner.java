package christmas.domain.benefit;

import christmas.EventDayValidator;
import christmas.view.ConsoleInput;
import christmas.view.ConsoleOutput;
import christmas.view.InputValidator;
import christmas.view.Parser;

public class EventPlanner {
    private ConsoleInput consoleInput = new ConsoleInput();
    private ConsoleOutput consoleOutput = new ConsoleOutput();

    public void startOrder() {
        consoleOutput.greeting();
        int orderDayOfMonth = getOrderDayOfMonth();
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
                consoleOutput.printError(e.getMessage());
            }
        }
    }
}
