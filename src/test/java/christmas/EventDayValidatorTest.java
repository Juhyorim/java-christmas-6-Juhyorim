package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.util.EventDayValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventDayValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {"1", "1 ", " 1", "2", "3", "5", "14", "25", "30", "31"})
    @DisplayName("1~31 날짜 정상동작")
    void goodCase(String dayOfMonth) {
        assertDoesNotThrow(() -> EventDayValidator.getValidDayOfMonth(dayOfMonth));
    }

    @ParameterizedTest
    @CsvSource(value = {"r", "ㄱ ", "ㄱ1", "hi"})
    @DisplayName("숫자가 아닐 때 예외")
    void notNumericException(String dayOfMonth) {
        assertThatThrownBy(() -> EventDayValidator.getValidDayOfMonth(dayOfMonth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EventDayValidator.NUMERIC_REQUIRED);
    }

    @ParameterizedTest
    @CsvSource(value = {"0", "32 ", "33"})
    @DisplayName("1일과 31일 사이가 아닐 때 예외")
    void notInEventPeriodException(String dayOfMonth) {
        assertThatThrownBy(() -> EventDayValidator.getValidDayOfMonth(dayOfMonth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EventDayValidator.NOT_IN_EVENT_PERIOD);
    }
}