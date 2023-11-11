package christmas.dto;

import christmas.domain.Menu;
import java.util.HashMap;
import java.util.Map;

public class DiscountedMenu {
    private Map<Menu, Integer> discountedMenuPrices;
    private int discountedTotalPrice;

    public DiscountedMenu() {
        this.discountedMenuPrices = new HashMap<>();
        this.discountedTotalPrice = 0;
    }

    public void discountPrice(Menu menu, Integer discountedPrice, Integer count) {
        this.discountedMenuPrices.put(menu, this.discountedMenuPrices.getOrDefault(menu, 0) + count * discountedPrice);
    }

    public void discountPrice(Menu menu, Integer discountedPrice) {
        this.discountedMenuPrices.put(menu, this.discountedMenuPrices.getOrDefault(menu, 0) + discountedPrice);
    }

    public void discountTotalPrice(int price) {
        this.discountedTotalPrice += price;
    }

    public void add(DiscountedMenu discountedMenuForAdd) {
        Map<Menu, Integer> discountedPriceForAdd = discountedMenuForAdd.discountedMenuPrices;

        for (Menu menu : discountedPriceForAdd.keySet()) {
            this.discountPrice(menu, discountedPriceForAdd.get(menu));
        }

        this.discountTotalPrice(discountedMenuForAdd.getDiscountedTotalPrice());
    }

    public int getDiscountedTotalPrice() {
        return discountedTotalPrice;
    }

    public int getDiscountedPrice(Menu menu) {
        return discountedMenuPrices.get(menu);
    }
}
