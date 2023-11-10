package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.dto.BenefitCheckDto;
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
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(10000, LocalDate.of(year, month, dayOfMonth));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

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
    void weekday(int year, int month, int dayOfMonth, boolean isWeekDay) {
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(10000, LocalDate.of(year, month, dayOfMonth));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        assertThat(possibleBenefits.contains(BenefitType.WEEK_DAY)).isEqualTo(isWeekDay);
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
    void weekend(int year, int month, int dayOfMonth, boolean isWeekend) {
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(10000, LocalDate.of(year, month, dayOfMonth));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        assertThat(possibleBenefits.contains(BenefitType.WEEKEND)).isEqualTo(isWeekend);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "2023:12:4:false", "2023:12:9:false", "2023:12:19:false", "2023:12:23:false", "2023:12:26:false",
                    "2023:12:3:true", "2023:12:10:true", "2023:12:17:true", "2023:12:25:true", "2023:12:31:true"
            },
            delimiter = ':'
    )
    @DisplayName("특별 할인 적용여부 확인")
    void specialDiscount(int year, int month, int dayOfMonth, boolean isContain) {
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(10000, LocalDate.of(year, month, dayOfMonth));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        assertThat(possibleBenefits.contains(BenefitType.SPECIAL)).isEqualTo(isContain);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "5000:false", "10000:false", "20000:false", "119999:false",
                    "120000:true", "120001:true", "130000:true"
            },
            delimiter = ':'
    )
    @DisplayName("증정 이벤트 적용여부 확인")
    void gift(int totalPrice, boolean canGetGift) {
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(totalPrice, LocalDate.of(2023, 12, 25));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        assertThat(possibleBenefits.contains(BenefitType.GIFT)).isEqualTo(canGetGift);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "10", "23", "9999"})
    @DisplayName("가격이 기준치(10000원) 미만이면 아무것도 적용되지 않음")
    void priceUnderMinPrice(int totalPrice) {
        BenefitCheckDto benefitCheckDto = new BenefitCheckDto(totalPrice, LocalDate.of(2023, 12, 25));
        List<BenefitType> possibleBenefits = BenefitType.getPossibleBenefits(benefitCheckDto);

        assertThat(possibleBenefits.size()).isEqualTo(0);
    }
}