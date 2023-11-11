package christmas;

public class EventDayValidator {
    private static final int EVENT_START_DAY = 1;
    private static final int EVENT_LAST_DAY = 31;
    public static final String NUMERIC_REQUIRED = "날짜는 숫자여야 합니다";
    public static final String NOT_IN_EVENT_PERIOD = "이벤트 기간이 아닙니다";

    public static void validate(String input) {
        validateIsNumeric(input);
        validateEventPeriod(input);
    }

    private static void validateIsNumeric(String input) {
        if (input.matches("[0-9]+") == false) {
            throw new IllegalArgumentException(NUMERIC_REQUIRED);
        }
    }

    private static void validateEventPeriod(String input) {
        Integer inputNumber = Integer.parseInt(input);
        if (inputNumber < EVENT_START_DAY || inputNumber > EVENT_LAST_DAY) {
            throw new IllegalArgumentException(NOT_IN_EVENT_PERIOD);
        }
    }
}
