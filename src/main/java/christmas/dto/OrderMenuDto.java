package christmas.dto;

import christmas.domain.OrderedMenu;
import christmas.domain.menu.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMenuDto {
    private Map<String, Integer> menus = new HashMap<>();

    private OrderMenuDto() {

    }

    public static OrderMenuDto convert(OrderedMenu orderedMenu) {
        OrderMenuDto orderMenuDto = new OrderMenuDto();
        for (Menu menu : orderedMenu.getKindOfMenu()) {
            orderMenuDto.menus.put(menu.getName(), orderedMenu.getCount(menu));
        }
        return orderMenuDto;
    }

    public List<String> getKindOfMenu() {
        return new ArrayList<>(menus.keySet());
    }

    public Integer getCount(String menuName) {
        return menus.get(menuName);
    }
}
