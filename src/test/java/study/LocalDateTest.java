package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LocalDateTest {
    private final List<DayOfWeek> weekdays = List.of(
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
    );

    private final List<DayOfWeek> weekends = List.of(
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
    );

    @Test
    @DisplayName("연월일 세팅 테스트")
    void yearMonthDaySetting() {
        LocalDate date = LocalDate.of(2023, 12, 25);

        assertThat(date.getYear()).isEqualTo(2023);
        assertThat(date.getMonthValue()).isEqualTo(12);
        assertThat(date.getDayOfMonth()).isEqualTo(25);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "10", "23", "24"})
    @DisplayName("before 테스트")
    void beforeTest(int day) {
        LocalDate lastDate = LocalDate.of(2023, 12, 25);
        LocalDate date = LocalDate.of(2023, 12, day);

        //25는 포함되지 않는다
        assertThat(date.isBefore(lastDate)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"2", "10", "24"})
    @DisplayName("after 테스트")
    void afterTest(int day) {
        LocalDate firstDate = LocalDate.of(2023, 12, 1);
        LocalDate date = LocalDate.of(2023, 12, day);

        //1은 포함되지 않는다
        assertThat(date.isAfter(firstDate)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "10", "23", "24", "25"})
    @DisplayName("between 테스트")
    void betweenTest(int day) {
        LocalDate firstDate = LocalDate.of(2023, 12, 1);
        LocalDate lastDate = LocalDate.of(2023, 12, 25);
        LocalDate date = LocalDate.of(2023, 12, day);

        assertThat(date.isAfter(firstDate) || date.isEqual(firstDate)).isTrue();
        assertThat(date.isBefore(lastDate) || date.isEqual(lastDate)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "2023:12:3:true", "2023:12:4:true", "2023:12:7:true", "2023:12:10:true", "2023:12:14:true",
                    "2023:12:1:false", "2023:12:9:false", "2023:12:15:false", "2023:12:23:false", "2023:12:30:false"
            },
            delimiter = ':'
    )
    @DisplayName("평일 확인 테스트")
    void weekdayTest(int year, int month, int dayOfMonth, boolean isWeekday) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        assertThat(weekdays.contains(localDate.getDayOfWeek())).isEqualTo(isWeekday);
    }
}
