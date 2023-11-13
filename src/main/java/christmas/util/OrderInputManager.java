package christmas.util;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuGroup;
import christmas.view.message.ErrorMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInputManager {
    private static final int MINIMUN_ORDER_COUNT = 20;
    private static final char MENU_AND_COUNT_DELIMITER = '-';
    private static final char MENU_DELIMITER = ',';
    private static final String NUMERIC_REGEX = "[0-9]+";

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
                throw new IllegalArgumentException(ErrorMessage.UNDER_20_REQUIRED.getMessage());
            }
        }

        validateMenuCombination(menuAndCount);

        return menuAndCount;
    }

    private static void validateMenuCombination(Map<Menu, Integer> menuAndCount) {
        boolean containOnlyDrink = true;
        for (Menu menu : menuAndCount.keySet()) {
            if (MenuGroup.findMenuGroup(menu) != MenuGroup.DRINK) {
                containOnlyDrink = false;
            }
        }

        if (containOnlyDrink) {
            throw new IllegalArgumentException(ErrorMessage.ONLY_DRINK_ORDER_NOT_ALLOWED.getMessage());
        }
    }

    private static int addMenuAndCount(Map<Menu, Integer> menuAndCount, String menuCountBundle) {
        List<String> splitedMenuCount = SplitManager.split(menuCountBundle, MENU_AND_COUNT_DELIMITER);

        if (splitedMenuCount.size() != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORM.getMessage());
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
            throw new IllegalArgumentException(ErrorMessage.DUPLICATED_MENU_NOT_ALLOWED.getMessage());
        }
    }

    private static Menu parseMenu(String menuName) {
        menuName = menuName.trim();

        Menu menu = Menu.getMenu(menuName);
        if (menu == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALiD_MENU.getMessage());
        }

        return menu;
    }

    private static int parseMenuCount(String count) {
        count = count.trim();

        if (count.matches(NUMERIC_REGEX) == false) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MENU_COUNT.getMessage());
        }

        int menuCount = Integer.parseInt(count);
        if (menuCount == 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MENU_COUNT.getMessage());
        }

        return menuCount;
    }

    private static List<String> menuCountBundleSplit(String input) {
        return SplitManager.split(input, MENU_DELIMITER);
    }
}
