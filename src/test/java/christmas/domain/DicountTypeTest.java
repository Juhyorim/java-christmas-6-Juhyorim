package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.benefit.discount.DiscountType;
import christmas.dto.DiscountCheck;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DicountTypeTest {
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
        DiscountCheck benefitCheckDto = new DiscountCheck(10000, LocalDate.of(year, month, dayOfMonth));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.contains(DiscountType.CHRISTMAS_D_DAY)).isEqualTo(isContain);
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
        DiscountCheck benefitCheckDto = new DiscountCheck(10000, LocalDate.of(year, month, dayOfMonth));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.contains(DiscountType.WEEK_DAY)).isEqualTo(isWeekDay);
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
        DiscountCheck benefitCheckDto = new DiscountCheck(10000, LocalDate.of(year, month, dayOfMonth));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.contains(DiscountType.WEEKEND)).isEqualTo(isWeekend);
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
        DiscountCheck benefitCheckDto = new DiscountCheck(10000, LocalDate.of(year, month, dayOfMonth));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.contains(DiscountType.SPECIAL)).isEqualTo(isContain);
    }


    @ParameterizedTest
    @CsvSource(value = {"1", "2", "10", "23", "9999"})
    @DisplayName("가격이 기준치(10000원) 미만이면 아무것도 적용되지 않음")
    void priceUnderMinPrice(int totalPrice) {
        DiscountCheck benefitCheckDto = new DiscountCheck(totalPrice, LocalDate.of(2023, 12, 25));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.size()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "2022:12:4", "2022:12:9", "2022:12:19", "2024:12:23", "2024:12:26",
                    "2023:11:3", "2023:9:10", "2023:1:17", "2023:2:25", "2023:3:31"
            },
            delimiter = ':'
    )
    @DisplayName("2023년 12월이 아니면 아무것도 적용되지 않음")
    void test12(int year, int month, int dayOfMonth) {
        DiscountCheck benefitCheckDto = new DiscountCheck(1000000, LocalDate.of(year, month, dayOfMonth));
        List<DiscountType> possibleBenefits = DiscountType.getPossibleDiscount(benefitCheckDto);

        assertThat(possibleBenefits.size()).isEqualTo(0);
    }
}