package christmas.util;

import christmas.domain.Menu;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInputManager {
    private static final int MINIMUN_ORDER_COUNT = 20;
    public static final String INVALiD_MENU = "해당하는 메뉴가 존재하지 않습니다.";
    public static final String INVALID_MENU_COUNT = "메뉴 개수가 올바르지 않습니다.";
    public static final String UNDER_20_REQUIRED = "메뉴 개수는 20개 이하여야합니다.";

    public static Map<Menu, Integer> getValidOrder(String input) {
        List<String> menuCountBundle = menuCountBundleSplit(input);
        return menuAndCountSplit(menuCountBundle);
    }

    private static Map<Menu, Integer> menuAndCountSplit(List<String> menuCountBundles) {
        Map<Menu, Integer> menuAndCount = new HashMap<>();
        int totalCount = 0;

        for (String menuCountBundle : menuCountBundles) {
            totalCount += addMenuAndCount(menuAndCount, menuCountBundle);
            if (totalCount > MINIMUN_ORDER_COUNT) {
                throw new IllegalArgumentException(UNDER_20_REQUIRED);
            }
        }

        return menuAndCount;
    }

    private static int addMenuAndCount(Map<Menu, Integer> menuAndCount, String menuCountBundle) {
        List<String> splitedMenuCount = SplitManager.split(menuCountBundle, '-');
        String menu = splitedMenuCount.get(0);
        String count = splitedMenuCount.get(1);

        Menu validMenu = parseMenu(menu);
        int validCount = parseMenuCount(count);
        menuAndCount.put(validMenu, validCount);

        return validCount;
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

        if (count.matches("[0-9]+") == false) {
            throw new IllegalArgumentException(INVALID_MENU_COUNT);
        }

        int menuCount = Integer.parseInt(count);
        if (menuCount == 0) {
            throw new IllegalArgumentException(INVALID_MENU_COUNT);
        }

        return menuCount;
    }

    private static List<String> menuCountBundleSplit(String input) {
        return SplitManager.split(input, ',');
    }
}
