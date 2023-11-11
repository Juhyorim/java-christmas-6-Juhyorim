package christmas.domain;

import christmas.domain.benefit.BenefitType;
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
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
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
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("평일 할인만 적용 테스트")
    void weekDay(int count) {
        //given
        DiscountCalculator discountCalculator = new DiscountCalculator();
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 4));

        //when
        orderForm.addMenu(Menu.ICE_CREAM, count);
        List<BenefitType> benefits = List.of(BenefitType.WEEK_DAY);
        DiscountedMenu discountedMenu = discountCalculator.applyDiscount(orderForm, benefits);

        //then
        Assertions.assertThat(discountedMenu.getDiscountedPrice(Menu.ICE_CREAM)).isEqualTo(2023 * count);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("주말 할인만 적용 테스트")
    void weekendBenefitOnly(int count) {
        //given
        DiscountCalculator discountCalculator = new DiscountCalculator();
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 8));

        //when
        orderForm.addMenu(Menu.T_BONE_STEAK, count);
        List<BenefitType> benefits = List.of(BenefitType.WEEKEND);
        DiscountedMenu discountedMenu = discountCalculator.applyDiscount(orderForm, benefits);

        //then
        Assertions.assertThat(discountedMenu.getDiscountedPrice(Menu.T_BONE_STEAK)).isEqualTo(2023 * count);
    }

    @Test
    @DisplayName("스패셜 할인만 적용 테스트")
    void specialBenefitOnly() {
        //given
        DiscountCalculator discountCalculator = new DiscountCalculator();
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 10));

        //when
        orderForm.addMenu(Menu.T_BONE_STEAK, 2);
        List<BenefitType> benefits = List.of(BenefitType.SPECIAL);
        DiscountedMenu discountedMenu = discountCalculator.applyDiscount(orderForm, benefits);

        //then
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }
}