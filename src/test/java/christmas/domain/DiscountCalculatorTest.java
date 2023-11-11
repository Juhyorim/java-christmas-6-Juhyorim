package christmas.domain;

import christmas.dto.DiscountedMenu;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountCalculatorTest {
    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("크리스마스 할인 적용 테스트")
    void christmasDDay(int count) {
        //given
        DiscountCalculator discountCalculator = new DiscountCalculator();
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 1));

        //when
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, count);
        List<BenefitType> benefits = List.of(BenefitType.CHRISTMAS_D_DAY);
        DiscountedMenu discountedMenu = discountCalculator.applyDiscount(orderForm, benefits);

        //then
        Assertions.assertThat(discountedMenu.getTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("크리스마스 할인 적용 종류 여러개 테스트")
    void christmasDDayMany() {
        //given
        DiscountCalculator discountCalculator = new DiscountCalculator();
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 1));

        //when
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, 1);
        orderForm.addMenu(Menu.SEAFOOD_PASTA, 1);
        orderForm.addMenu(Menu.CAESAR_SALAD, 1);
        List<BenefitType> benefits = List.of(BenefitType.CHRISTMAS_D_DAY);
        DiscountedMenu discountedMenu = discountCalculator.applyDiscount(orderForm, benefits);

        //then
        Assertions.assertThat(discountedMenu.getTotalPrice()).isEqualTo(1000);
    }
}