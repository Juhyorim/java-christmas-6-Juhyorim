package christmas.domain;

import christmas.domain.benefit.EventBadge;
import christmas.domain.benefit.gift.PossibleGift;
import christmas.dto.GiftDto;
import christmas.dto.OrderMenuDto;
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
        Order order = getValidOrder(orderDayOfMonth);
        TotalBenefits benefits = benefitManager.getBenefits(order);

        printResult(order, benefits);
    }

    private void printResult(Order order, TotalBenefits benefits) {
        consoleOutput.printResultStart(order.getOrderDayOfMonth());
        printOrderMenu(order);
        printTotalPriceBeforeDiscount(order);
        printGift(benefits);
        printBenefits(benefits);
        printTotalBenefitPrice(benefits);
        printActualPaymentAmount(order, benefits);
        printEventBadge(benefits);
    }

    private void printOrderMenu(Order order) {
        OrderMenuDto orderMenuDto = OrderMenuDto.convert(order.getMenus());
        consoleOutput.printOrderMenu(orderMenuDto);
    }

    private void printTotalPriceBeforeDiscount(Order order) {
        consoleOutput.printTotalPriceBeforeDiscount(order.getTotalPrice());
    }

    private void printTotalBenefitPrice(TotalBenefits benefits) {
        consoleOutput.printTotalBenefitPrice(benefits.getTotalBenefitPrice());
    }

    private void printGift(TotalBenefits benefits) {
        PossibleGift gifts = benefits.getGifts();
        GiftDto giftDto = GiftDto.convert(gifts);

        consoleOutput.printGift(giftDto);
    }

    private void printBenefits(TotalBenefits benefits) {
        consoleOutput.printBenefits(benefits.getBenefits());
    }

    private void printActualPaymentAmount(Order order, TotalBenefits benefits) {
        int paymentAmountBefore = order.getTotalPrice();
        int actualDiscountPrice = benefits.getActualDiscountPrice(paymentAmountBefore);
        consoleOutput.printActualPaymentAmount(actualDiscountPrice);
    }

    private void printEventBadge(TotalBenefits benefits) {
        String eventBadgeName = benefits.getEventBadgeName();
        consoleOutput.printEventBadge(eventBadgeName);
    }

    private Order getValidOrder(int orderDayOfMonth) {
        Map<Menu, Integer> orders = getOrders();
        Order order = Order.make(LocalDate.of(2023, 12, orderDayOfMonth), orders);

        return order;
    }

    private Map<Menu, Integer> getOrders() {
        return (Map<Menu, Integer>) repeatedInput(
                () -> {
                    String input = consoleInput.getMenuAndCount();
                    return OrderInputManager.getValidOrder(input);
                },
                (e) -> consoleOutput.printInvalidOrderError(e.getMessage())
        );
    }

    private int getOrderDayOfMonth() {
        return (int) repeatedInput(
                () -> {
                    String input = consoleInput.getVisitDayOfMonth();
                    return EventDayValidator.getValidDayOfMonth(input);
                },
                (e) -> consoleOutput.printInvalidDayError()
        );
    }

    private Object repeatedInput(ValidInputGetter validInputGetter,
                                 ThrowInputError throwInputError) {
        while (true) {
            try {
                return validInputGetter.get();
            } catch (IllegalArgumentException e) {
                throwInputError.throwError(e);
            }
        }
    }
}
