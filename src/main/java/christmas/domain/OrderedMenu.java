package christmas.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderedMenu {
    private Map<Menu, Integer> menus;

    public OrderedMenu() {
        this.menus = new HashMap<>();
    }

    public void add(Menu menu, int count) {
        menus.put(menu, menus.getOrDefault(menu, 0) + count);
    }

    public List<Menu> getKindOfMenu() {
        List<Menu> kindOfMenu = new ArrayList<>();
        kindOfMenu.addAll(menus.keySet());

        return kindOfMenu;
    }

    public int getCount(Menu menu) {
        return menus.get(menu);
    }
}
