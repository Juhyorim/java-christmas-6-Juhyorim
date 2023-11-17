package christmas.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuGroupTest {
    @ParameterizedTest
    @CsvSource(
            value = {
                    "MUSHROOM_SOUP:APPETIZER", "TAPAS:APPETIZER", "CAESAR_SALAD:APPETIZER",
                    "T_BONE_STEAK:MAIN", "BARBECUE_RIBS:MAIN", "SEAFOOD_PASTA:MAIN", "CHRISTMAS_PASTA:MAIN",
                    "CHOCOLATE_CAKE:DESSERT", "ICE_CREAM:DESSERT",
                    "ZERO_COLA:DRINK", "RED_WINE:DRINK", "CHAMPAGNE:DRINK",
            },
            delimiter = ':'
    )
    @DisplayName("메뉴에 해당하는 메뉴그룹 찾기 테스트")
    void findMenuGroup(Menu menu, MenuGroup menuGroup) {
        assertThat(MenuGroup.findMenuGroup(menu)).isEqualTo(menuGroup);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "MUSHROOM_SOUP:APPETIZER", "TAPAS:APPETIZER", "CAESAR_SALAD:APPETIZER",
                    "T_BONE_STEAK:MAIN", "BARBECUE_RIBS:MAIN", "SEAFOOD_PASTA:MAIN", "CHRISTMAS_PASTA:MAIN",
                    "CHOCOLATE_CAKE:DESSERT", "ICE_CREAM:DESSERT",
                    "ZERO_COLA:DRINK", "RED_WINE:DRINK", "CHAMPAGNE:DRINK",
            },
            delimiter = ':'
    )
    @DisplayName("메뉴가 메뉴그룹에 속하는지 테스트")
    void menuIsInMenuGroup(Menu menu, MenuGroup menuGroup) {
        assertThat(MenuGroup.isInGroup(menu, menuGroup)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "MUSHROOM_SOUP:MAIN", "TAPAS:MAIN", "CAESAR_SALAD:MAIN",
                    "T_BONE_STEAK:DESSERT", "BARBECUE_RIBS:DESSERT", "SEAFOOD_PASTA:DESSERT", "CHRISTMAS_PASTA:DESSERT",
                    "CHOCOLATE_CAKE:DRINK", "ICE_CREAM:DRINK",
                    "ZERO_COLA:MAIN", "RED_WINE:MAIN", "CHAMPAGNE:MAIN",
            },
            delimiter = ':'
    )
    @DisplayName("메뉴가 메뉴그룹에 속하지 않는지 테스트")
    void menuIsNotInMenuGroup(Menu menu, MenuGroup menuGroup) {
        assertThat(MenuGroup.isInGroup(menu, menuGroup)).isFalse();
    }
}