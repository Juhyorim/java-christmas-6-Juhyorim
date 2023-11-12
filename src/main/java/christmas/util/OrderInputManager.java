package christmas.util;

import christmas.domain.Menu;
import christmas.domain.MenuGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInputManager {
    private static final int MINIMUN_ORDER_COUNT = 20;
    public static final char MENU_AND_COUNT_DELIMITER = '-';
    public static final char MENU_DELIMITER = ',';
    public static final String NUMERIC_REGEX = "[0-9]+";
    public static final String INVALiD_MENU = "해당하는 메뉴가 존재하지 않습니다.";
    public static final String INVALID_MENU_COUNT = "메뉴 개수가 올바르지 않습니다.";
    public static final String UNDER_20_REQUIRED = "메뉴 개수는 20개 이하여야합니다.";
    public static final String DUPLICATED_MENU_NOT_ALLOWED = "메뉴는 중복되어선 안됩니다.";
    public static final String ONLY_DRINK_ORDER_NOT_ALLOWED = "음료만 주문이 불가능합니다";

    public static Map<Menu, Integer> getValidOrder(String input) {
        List<String> menuCountBundle = menuCountBundleSplit(input);
        return convertMenuAndCount(menuCountBundle);
    }

    private static Map<Menu, Integer> convertMenuAndCount(List<String> menuCountBundles) {
        Map<Menu, Integer> menuAndCount = new HashMap<>();
        int totalCount = 0;

        for (String menuCountBundle : menuCountBundles) {
            totalCount += addMenuAndCount(menuAndCount, menuCountBundle);
            if (totalCount > MINIMUN_ORDER_COUNT) {
                throw new IllegalArgumentException(UNDER_20_REQUIRED);
            }
        }

        validateMenuCombination(menuAndCount);

        return menuAndCount;
    }

    private static void validateMenuCombination(Map<Menu, Integer> menuAndCount) {
        boolean containOnlyDrink = true;
        for (Menu menu: menuAndCount.keySet()) {
            if (MenuGroup.findMenuGroup(menu) != MenuGroup.DRINK) {
                containOnlyDrink = false;
            }
        }

        if (containOnlyDrink) {
            throw new IllegalArgumentException(ONLY_DRINK_ORDER_NOT_ALLOWED);
        }
    }

    private static int addMenuAndCount(Map<Menu, Integer> menuAndCount, String menuCountBundle) {
        List<String> splitedMenuCount = SplitManager.split(menuCountBundle, MENU_AND_COUNT_DELIMITER);

        if (splitedMenuCount.size() != 2) {
            throw new IllegalArgumentException("입력형식이 올바르지 않습니다");
        }

        String menu = splitedMenuCount.get(0);
        String count = splitedMenuCount.get(1);

        Menu validMenu = parseMenu(menu);
        int validCount = parseMenuCount(count);

        validateDuplicatedMenu(menuAndCount, validMenu);
        menuAndCount.put(validMenu, validCount);

        return validCount;
    }

    private static void validateDuplicatedMenu(Map<Menu, Integer> menuAndCount, Menu validMenu) {
        if (menuAndCount.get(validMenu) != null) {
            throw new IllegalArgumentException(DUPLICATED_MENU_NOT_ALLOWED);
        }
    }

    private static Menu parseMenu(String menuName) {
        menuName = menuName.trim();

        Menu menu = Menu.getMenu(menuName);
        if (menu == null) {
            throw new IllegalArgumentException(INVALiD_MENU);
        }

        return menu;
    }

    private static int parseMenuCount(String count) {
        count = count.trim();

        if (count.matches(NUMERIC_REGEX) == false) {
            throw new IllegalArgumentException(INVALID_MENU_COUNT);
        }

        int menuCount = Integer.parseInt(count);
        if (menuCount == 0) {
            throw new IllegalArgumentException(INVALID_MENU_COUNT);
        }

        return menuCount;
    }

    private static List<String> menuCountBundleSplit(String input) {
        return SplitManager.split(input, MENU_DELIMITER);
    }
}
