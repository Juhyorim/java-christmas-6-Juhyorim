package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderFormTest {
    @Test
    @DisplayName("총주문금액 테스트")
    void totalPrice() {
        OrderForm orderForm = new OrderForm(LocalDate.of(2023, 12, 1));
        orderForm.addMenu(Menu.CHRISTMAS_PASTA, 1);
        orderForm.addMenu(Menu.CAESAR_SALAD, 1);
        orderForm.addMenu(Menu.T_BONE_STEAK, 3);

        int totalPrice =
                Menu.CHRISTMAS_PASTA.getPrice() + Menu.CAESAR_SALAD.getPrice() + Menu.T_BONE_STEAK.getPrice() * 3;

        assertThat(orderForm.getTotalPrice()).isEqualTo(totalPrice);
    }
}