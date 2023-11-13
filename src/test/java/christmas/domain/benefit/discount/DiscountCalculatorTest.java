package christmas.domain.benefit.discount;

import static org.assertj.core.api.Assertions.assertThat;

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
        Order order = new Order(LocalDate.of(2023, 12, 1));

        //when
        order.addMenu(Menu.CHRISTMAS_PASTA, count);
        List<DiscountType> benefits = List.of(DiscountType.CHRISTMAS_D_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(order, benefits).getDiscountedMenus();

        //then
        assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("크리스마스 할인 적용 종류 여러개 테스트")
    void christmasDDayMany() {
        //given
        Order order = new Order(LocalDate.of(2023, 12, 1));

        //when
        order.addMenu(Menu.CHRISTMAS_PASTA, 1);
        order.addMenu(Menu.SEAFOOD_PASTA, 1);
        order.addMenu(Menu.CAESAR_SALAD, 1);
        List<DiscountType> benefits = List.of(DiscountType.CHRISTMAS_D_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(order, benefits).getDiscountedMenus();

        //then
        assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("평일 할인만 적용 테스트")
    void weekDay(int count) {
        //given
        Order order = new Order(LocalDate.of(2023, 12, 4));

        //when
        order.addMenu(Menu.ICE_CREAM, count);
        List<DiscountType> benefits = List.of(DiscountType.WEEK_DAY);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(order, benefits).getDiscountedMenus();

        //then
        assertThat(discountedMenu.getDiscountedPrice(Menu.ICE_CREAM)).isEqualTo(2023 * count);
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3", "10", "20"})
    @DisplayName("주말 할인만 적용 테스트")
    void weekendBenefitOnly(int count) {
        //given
        Order order = new Order(LocalDate.of(2023, 12, 8));

        //when
        order.addMenu(Menu.T_BONE_STEAK, count);
        List<DiscountType> benefits = List.of(DiscountType.WEEKEND);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(order, benefits).getDiscountedMenus();

        //then
        assertThat(discountedMenu.getDiscountedPrice(Menu.T_BONE_STEAK)).isEqualTo(2023 * count);
    }

    @Test
    @DisplayName("스패셜 할인만 적용 테스트")
    void specialBenefitOnly() {
        //given
        Order order = new Order(LocalDate.of(2023, 12, 10));

        //when
        order.addMenu(Menu.T_BONE_STEAK, 2);
        List<DiscountType> benefits = List.of(DiscountType.SPECIAL);
        DiscountedMenus discountedMenu = DiscountCalculator.applyDiscount(order, benefits).getDiscountedMenus();

        //then
        assertThat(discountedMenu.getDiscountedTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("DiscountType별 할인금액 저장 테스트1")
    void discountPriceByDiscountType() {
        Order order = new Order(LocalDate.of(2023, 12, 3));
        order.addMenu(Menu.BARBECUE_RIBS, 1);
        order.addMenu(Menu.SEAFOOD_PASTA, 1);
        order.addMenu(Menu.ICE_CREAM, 1);
        order.addMenu(Menu.TAPAS, 1);
        TotalDiscount totalDiscount = DiscountCalculator.applyDiscount(
                order,
                List.of(DiscountType.SPECIAL, DiscountType.WEEKEND, DiscountType.CHRISTMAS_D_DAY)
        );
        //총주문 1000원 //메인메뉴 2023 * 2원 할인 //크리스마스 1200원

        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.SPECIAL)).isEqualTo(1000);
        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.WEEKEND)).isEqualTo(2023 * 2);
        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.CHRISTMAS_D_DAY)).isEqualTo(1200);
        assertThat(totalDiscount.getTotalDiscountPrice()).isEqualTo(1000 + 2023*2 + 1200);
    }

    @Test
    @DisplayName("DiscountType별 할인금액 저장 테스트2")
    void discountPriceByDiscountType2() {
        Order order = new Order(LocalDate.of(2023, 12, 25));
        order.addMenu(Menu.BARBECUE_RIBS, 1);
        order.addMenu(Menu.CHOCOLATE_CAKE, 1);
        order.addMenu(Menu.ICE_CREAM, 1);
        order.addMenu(Menu.TAPAS, 1);
        TotalDiscount totalDiscount = DiscountCalculator.applyDiscount(
                order,
                List.of(DiscountType.SPECIAL, DiscountType.WEEK_DAY, DiscountType.CHRISTMAS_D_DAY)
        );
        //총주문 1000원 //디저트 2023 * 2원 할인 //크리스마스 3400원

        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.SPECIAL)).isEqualTo(1000);
        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.WEEK_DAY)).isEqualTo(2023 * 2);
        assertThat(totalDiscount.getgetDiscountPriceByDiscountType().get(DiscountType.CHRISTMAS_D_DAY)).isEqualTo(3400);
        assertThat(totalDiscount.getTotalDiscountPrice()).isEqualTo(1000 + 2023*2 + 3400);
    }
}