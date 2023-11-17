package christmas.domain.menu;

import java.util.List;

public enum MenuGroup {
    APPETIZER(List.of(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESAR_SALAD)),
    MAIN(List.of(Menu.T_BONE_STEAK, Menu.BARBECUE_RIBS, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT(List.of(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM)),
    DRINK(List.of(Menu.ZERO_COLA, Menu.RED_WINE, Menu.CHAMPAGNE));

    public static final String MENU_NOT_IN_MENU_GROUP = "메뉴에 해당하는 그룹이 없습니다";
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

        throw new IllegalArgumentException(MENU_NOT_IN_MENU_GROUP);
    }

    public static boolean isInGroup(Menu menu, MenuGroup menuGroup) {
        return menuGroup.menus.contains(menu);
    }
}
