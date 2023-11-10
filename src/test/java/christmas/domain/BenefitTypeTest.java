package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BenefitTypeTest {
    @ParameterizedTest
    @CsvSource(
            value = {
                    "2023:11:19:false", "2023:11:21:false", "2023:12:1:true", "2023:12:2:true", "2023:12:16:true",
                    "2023:12:23:true", "2023:12:24:true", "2023:12:25:true", "2023:12:26:false", "2023:12:27:false"
            },
            delimiter = ':'
    )
    @DisplayName("크리스마스 디데이 적용여부 확인")
    void christmasDDay(int year, int month, int dayOfMonth, boolean isContain) {
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(
                10000,
                LocalDate.of(year, month, dayOfMonth)
        );

        assertThat(possibleBenefits.contains(BenefitType.CHRISTMAS_D_DAY)).isEqualTo(isContain);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "2023:12:3:true", "2023:12:4:true", "2023:12:7:true", "2023:12:10:true", "2023:12:14:true",
                    "2023:12:1:false", "2023:12:9:false", "2023:12:15:false", "2023:12:23:false", "2023:12:30:false"
            },
            delimiter = ':'
    )
    @DisplayName("평일할인 적용여부 확인")
    void weekday(int year, int month, int dayOfMonth, boolean isContain) {
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(
                10000,
                LocalDate.of(year, month, dayOfMonth)
        );

        assertThat(possibleBenefits.contains(BenefitType.WEEK_DAY)).isEqualTo(isContain);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "2023:12:3:false", "2023:12:4:false", "2023:12:7:false", "2023:12:10:false", "2023:12:14:false",
                    "2023:12:1:true", "2023:12:9:true", "2023:12:15:true", "2023:12:23:true", "2023:12:30:true"
            },
            delimiter = ':'
    )
    @DisplayName("주말할인 적용여부 확인")
    void weekend(int year, int month, int dayOfMonth, boolean isContain) {
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(
                10000,
                LocalDate.of(year, month, dayOfMonth)
        );

        assertThat(possibleBenefits.contains(BenefitType.WEEKEND)).isEqualTo(isContain);
    }
}