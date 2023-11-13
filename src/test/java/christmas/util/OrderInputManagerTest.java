package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.menu.Menu;
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
    @CsvSource(value = {"양송이수프-20-1,타파스-1", "양송이수프-1-9,타파스-2", "양송이수프,타파스-1"}, delimiter = ':')
    @DisplayName("형식에 맞지 않는 입력 예외발생")
    void invalidInput(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"양송이숲-1,타파스-1", "양송이수프-1,타노스-1", "양송이밥-1,타파스-1", "안녕-1"}, delimiter = ':')
    @DisplayName("올바르지 않은 메뉴 입력 시 예외")
    void invalidMenuThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당하는 메뉴가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"양송이수프-0,타파스-1", "양송이수프-r,타파스-1", "양송이수프-dds,타파스-1"}, delimiter = ':')
    @DisplayName("메뉴 개수가 이상할 때 예외발생")
    void invalidMenuCountThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 개수가 올바르지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"양송이수프-20,타파스-1", "양송이수프-19,타파스-2", "양송이수프-111,타파스-1"}, delimiter = ':')
    @DisplayName("메뉴 개수가 20개를 초과할 때 예외발생")
    void OverMenuCountThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 개수는 20개 이하여야합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"양송이수프-20,양송이수프-1", "타파스-19,타파스-2", "초코케이크-2,초코케이크-1"}, delimiter = ':')
    @DisplayName("메뉴가 중복될 때 예외발생")
    void DuplicatedMenuThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴는 중복되어선 안됩니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"제로콜라-10", "레드와인-2", "제로콜라-3,레드와인-1"}, delimiter = ':')
    @DisplayName("음료만 주문 시 예외발생")
    void onlyDrinkOrderThrowException(String menuAndCountInput) {
        assertThatThrownBy(() -> OrderInputManager.getValidOrder(menuAndCountInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음료만 주문이 불가능합니다");
    }
}