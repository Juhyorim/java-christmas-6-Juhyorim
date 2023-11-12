package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.Menu;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource(value = {"양송이숲-1,타파스-1", "양송이수프-1,타노스-1", "양송이밥-1,타파스-1", "안녕-1"}, delimiter = ':')
    @DisplayName("올바르지 않은 메뉴 입력 시 예외")
    void invalidMenuThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당하는 메뉴가 존재하지 않습니다.");
    }
}