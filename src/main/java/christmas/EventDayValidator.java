package christmas;

public class EventDayValidator {
    public static final String NUMERIC_REQUIRED = "날짜는 숫자여야 합니다";

    public static void validate(String input) {
        validateIsNumeric(input);
    }

    private static void validateIsNumeric(String input) {
        if (input.matches("[0-9]+") == false) {
            throw new IllegalArgumentException(NUMERIC_REQUIRED);
        }
    }
}
