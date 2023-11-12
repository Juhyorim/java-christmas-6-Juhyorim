package christmas.util;

import christmas.domain.Menu;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInputManager {
    public static Map<Menu, Integer> getValidOrder(String input) {
        List<String> menuCountBundle = menuCountBundleSplit(input);
        return menuAndCountSplit(menuCountBundle);
    }

    private static Map<Menu, Integer> menuAndCountSplit(List<String> menuCountBundles) {
        Map<Menu, Integer> menuAndCount = new HashMap<>();

        for (String menuCountBundle : menuCountBundles) {
            List<String> splitedMenuCount = SplitManager.split(menuCountBundle, '-');
            String menu = splitedMenuCount.get(0);
            String count = splitedMenuCount.get(1);

            Menu validMenu = parseMenu(menu);
            int validCount = Integer.parseInt(count);
            menuAndCount.put(validMenu, validCount);
        }

        return menuAndCount;
    }

    private static Menu parseMenu(String menuName) {
        menuName = menuName.trim();

        Menu menu = Menu.getMenu(menuName);
        if (menu == null) {
            throw new IllegalArgumentException("해당하는 메뉴가 존재하지 않습니다.");
        }

        return menu;
    }

    private static List<String> menuCountBundleSplit(String input) {
        return SplitManager.split(input, ',');
    }
}
