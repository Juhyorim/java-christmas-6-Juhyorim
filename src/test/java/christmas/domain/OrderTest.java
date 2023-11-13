package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Test
    @DisplayName("메뉴와 개수를 잘 담는지 테스트")
    void menuCount() {
        Order orderForm = new Order(LocalDate.of(2023, 12, 1));
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, 1);
        orderForm.addMenu(Menu.CAESAR_SALAD, 1);
        orderForm.addMenu(Menu.T_BONE_STEAK, 3);

        OrderedMenu menus = orderForm.getMenus();

        assertThat(menus.getKindOfMenu()).contains(Menu.CHRISTMAS_PASTA, Menu.CAESAR_SALAD, Menu.T_BONE_STEAK);
        assertThat(menus.getCount(Menu.CHRISTMAS_PASTA)).isEqualTo(1);
        assertThat(menus.getCount(Menu.CAESAR_SALAD)).isEqualTo(1);
        assertThat(menus.getCount(Menu.T_BONE_STEAK)).isEqualTo(3);
    }

    @Test
    @DisplayName("총주문금액 테스트")
    void totalPrice() {
        Order orderForm = new Order(LocalDate.of(2023, 12, 1));
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, 1);
        orderForm.addMenu(Menu.CAESAR_SALAD, 1);
        orderForm.addMenu(Menu.T_BONE_STEAK, 3);

        int totalPrice =
                Menu.CHRISTMAS_PASTA.getPrice() + Menu.CAESAR_SALAD.getPrice() + Menu.T_BONE_STEAK.getPrice() * 3;

        assertThat(orderForm.getTotalPrice()).isEqualTo(totalPrice);
    }
}