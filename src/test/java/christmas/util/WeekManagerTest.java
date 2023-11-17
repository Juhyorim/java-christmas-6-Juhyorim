package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WeekManagerTest {
    @ParameterizedTest
    @CsvSource(
            value = {
                    "MONDAY:true", "TUESDAY:true", "WEDNESDAY:true", "THURSDAY:true",
                    "FRIDAY:false", "SATURDAY:false", "SUNDAY:true"
            },
            delimiter = ':'
    )
    @DisplayName("평일, 주말 구분 테스트")
    void isWeekDay(DayOfWeek dayOfWeek, boolean isWeekDay) {
        assertThat(WeekManager.isWeekday(dayOfWeek)).isEqualTo(isWeekDay);
    }
}