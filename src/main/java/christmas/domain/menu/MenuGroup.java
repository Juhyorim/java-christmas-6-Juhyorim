package christmas.domain.menu;

import java.util.List;

public enum MenuGroup {
    APPETIZER(List.of(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESAR_SALAD)),
    MAIN(List.of(Menu.T_BONE_STEAK, Menu.BARBECUE_RIBS, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT(List.of(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM)),
    DRINK(List.of(Menu.ZERO_COLA, Menu.RED_WINE, Menu.CHAMPAGNE));

    private List<Menu> menus;

    private MenuGroup(List<Menu> menus) {
        this.menus = menus;
    }

    public static MenuGroup findMenuGroup(Menu menu) {
        for (MenuGroup menuGroup : values()) {
            if (menuGroup.menus.contains(menu)) {
                return menuGroup;
            }
        }

        throw new IllegalArgumentException("[ERROR] 메뉴에 해당하는 그룹이 없습니다");
    }

    public static boolean isInGroup(Menu menu, MenuGroup menuGroup) {
        return menuGroup.menus.contains(menu);
    }
}
