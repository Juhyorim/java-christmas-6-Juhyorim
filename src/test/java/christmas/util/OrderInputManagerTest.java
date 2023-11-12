package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderInputManagerTest {
    @Test
    @DisplayName("메뉴와 개수 잘 쪼개는지 확인")
    void splitTest() {
        Map<Menu, Integer> validOrder = OrderInputManager.getValidOrder("양송이수프-1,타파스-1");
        Set<Menu> menus = validOrder.keySet();

        assertThat(menus).contains(Menu.MUSHROOM_SOUP, Menu.TAPAS);
        assertThat(validOrder.get(Menu.MUSHROOM_SOUP)).isEqualTo(1);
        assertThat(validOrder.get(Menu.TAPAS)).isEqualTo(1);
    }
}