package christmas.dto;

import christmas.domain.Menu;
import java.util.HashMap;
import java.util.Map;

public class DiscountedMenu {
    private Map<Menu, Integer> discountedMenuPrice;
    private int discountedTotalPrice;

    public DiscountedMenu() {
        this.discountedMenuPrice = new HashMap<>();
        this.discountedTotalPrice = 0;
    }

    public void discountPrice(Menu menu, Integer amount) {
        discountedMenuPrice.put(menu, discountedMenuPrice.getOrDefault(menu, 0) + amount);
    }

    public void discountTotalPrice(int price) {
        this.discountedTotalPrice += price;
    }

    public void add(DiscountedMenu discountedMenuForAdd) {
        Map<Menu, Integer> discountedPriceForAdd = discountedMenuForAdd.discountedMenuPrice;

        for (Menu menu : discountedPriceForAdd.keySet()) {
            this.discountPrice(menu, discountedPriceForAdd.get(menu));
        }

        this.discountTotalPrice(discountedMenuForAdd.getTotalPrice());
    }

    public int getTotalPrice() {
        return discountedTotalPrice;
    }
}
