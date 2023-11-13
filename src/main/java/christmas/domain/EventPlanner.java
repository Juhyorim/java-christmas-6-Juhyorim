package christmas.domain;

import christmas.util.EventDayValidator;
import christmas.domain.benefit.BenefitManager;
import christmas.domain.menu.Menu;
import christmas.dto.TotalBenefits;
import christmas.util.OrderInputManager;
import christmas.view.ConsoleInput;
import christmas.view.ConsoleOutput;
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

        consoleOutput.printGift(benefits.getGifts());
        consoleOutput.printBenefits(benefits.getBenefits());
        consoleOutput.printTotalBenefitPrice(benefits.getTotalBenefitPrice());
        consoleOutput.printActualPaymentAmount(benefits.getActualDiscountPrice(orderForm.getTotalPrice()));
        consoleOutput.printEventBadge(benefits.getEventBadge().getName());
    }

    private Order getValidOrderForm(int orderDayOfMonth) {
        Map<Menu, Integer> orders = getOrders();
        Order orderForm = Order.make(LocalDate.of(2023, 12, orderDayOfMonth), orders);

        return orderForm;
    }

    private Map<Menu, Integer> getOrders() {
        return (Map<Menu, Integer>) repeatedInput(
                () -> consoleOutput.menuAndCountRequest(),
                () -> {
                    String input = consoleInput.getMenuAndCount();
                    return OrderInputManager.getValidOrder(input);
                },
                (e) -> consoleOutput.printInvalidOrderError(e.getMessage())
        );
    }

    private int getOrderDayOfMonth() {
        return (int) repeatedInput(
                () -> consoleOutput.visitDayRequest(),
                () -> {
                    String input = consoleInput.getVisitDayOfMonth();
                    return EventDayValidator.getValidDayOfMonth(input);
                },
                (e) -> consoleOutput.printInvalidDayError()
        );
    }

    private Object repeatedInput(RequestPrint requestPrint, ValidInputGetter validInputGetter,
                                 ThrowInputError throwInputError) {
        while (true) {
            try {
                requestPrint.print();
                return validInputGetter.get();
            } catch (IllegalArgumentException e) {
                throwInputError.throwError(e);
            }
        }
    }
}
