package christmas.domain.benefit.discount;

import christmas.domain.menu.Menu;
import christmas.domain.Order;
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
        Order orderForm = new Order(LocalDate.of(2023, 12, 1));

        //when
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, count);
        List<DiscountType> benefits = List.of(DiscountType.CHRISTMAS_D_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(orderForm, benefits).getDiscountedMenus();

        //then
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("크리스마스 할인 적용 종류 여러개 테스트")
    void christmasDDayMany() {
        //given
        Order orderForm = new Order(LocalDate.of(2023, 12, 1));

        //when
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, 1);
        orderForm.addMenu(Menu.SEAFOOD_PASTA, 1);
        orderForm.addMenu(Menu.CAESAR_SALAD, 1);
        List<DiscountType> benefits = List.of(DiscountType.CHRISTMAS_D_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(orderForm, benefits).getDiscountedMenus();

        //then
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("평일 할인만 적용 테스트")
    void weekDay(int count) {
        //given
        Order orderForm = new Order(LocalDate.of(2023, 12, 4));

        //when
        orderForm.addMenu(Menu.ICE_CREAM, count);
        List<DiscountType> benefits = List.of(DiscountType.WEEK_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(orderForm, benefits).getDiscountedMenus();

        //then
        Assertions.assertThat(discountedMenu.getDiscountedPrice(Menu.ICE_CREAM)).isEqualTo(2023 * count);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("주말 할인만 적용 테스트")
    void weekendBenefitOnly(int count) {
        //given
        Order orderForm = new Order(LocalDate.of(2023, 12, 8));

        //when
        orderForm.addMenu(Menu.T_BONE_STEAK, count);
        List<DiscountType> benefits = List.of(DiscountType.WEEKEND);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(orderForm, benefits).getDiscountedMenus();

        //then
        Assertions.assertThat(discountedMenu.getDiscountedPrice(Menu.T_BONE_STEAK)).isEqualTo(2023 * count);
    }

    @Test
    @DisplayName("스패셜 할인만 적용 테스트")
    void specialBenefitOnly() {
        //given
        Order orderForm = new Order(LocalDate.of(2023, 12, 10));

        //when
        orderForm.addMenu(Menu.T_BONE_STEAK, 2);
        List<DiscountType> benefits = List.of(DiscountType.SPECIAL);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(orderForm, benefits).getDiscountedMenus();

        //then
        Assertions.assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }
}