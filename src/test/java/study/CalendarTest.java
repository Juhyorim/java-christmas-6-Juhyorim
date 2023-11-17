package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Calendar 테스트")
public class CalendarTest {
    //deprecated 되었다고 함.. https://sujl95.tistory.com/3
    @Test
    @DisplayName("디폴트 동작 테스트")
    void defaultTest() {
        Calendar calendar = new GregorianCalendar();

        //오늘로 세팅되는군!
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(LocalDate.now().getYear());
        assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(LocalDate.now().getMonthValue());
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(LocalDate.now().getDayOfMonth());
    }

    @Test
    @DisplayName("월 세팅 테스트")
    void dateSetting() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2023);
    }

    @Test
    @DisplayName("년,월,일 세팅 테스트")
    void goodCase() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 25);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2023);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.DECEMBER);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(25);
    }
}
