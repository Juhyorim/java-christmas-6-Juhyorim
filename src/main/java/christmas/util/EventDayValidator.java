package christmas.util;

import christmas.view.message.ErrorMessage;
import java.time.LocalDate;

public class EventDayValidator {
    private static final LocalDate EVENT_START_DAY = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_LAST_DAY = LocalDate.of(2023, 12, 31);
    private static final String NUMERIC_REGEX = "[0-9]+";

    public static int getValidDayOfMonth(String input) {
        validateIsNumeric(input);
        validateDayOfMonth(input);

        return Integer.parseInt(input);
    }

    private static void validateIsNumeric(String input) {
        if (input.matches(NUMERIC_REGEX) == false) {
            throw new IllegalArgumentException(ErrorMessage.DATE_NUMERIC_REQUIRED.getMessage());
        }
    }

    private static void validateDayOfMonth(String dayOfMonthPeriod) {
        Integer inputNumber = Integer.parseInt(dayOfMonthPeriod);
        if (inputNumber < EVENT_START_DAY.getDayOfMonth() || inputNumber > EVENT_LAST_DAY.getDayOfMonth()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_IN_EVENT_PERIOD.getMessage());
        }
    }
}