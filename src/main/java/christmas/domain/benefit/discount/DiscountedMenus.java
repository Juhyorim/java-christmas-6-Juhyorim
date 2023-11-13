package christmas.domain.benefit.discount;

import christmas.domain.menu.Menu;
import java.util.HashMap;
import java.util.Map;

//할인된 메뉴와 할인금액을 저장
public class DiscountedMenus { 
    private Map<Menu, Integer> discountedMenus;
    private int discountedTotalPrice;

    public DiscountedMenus() {
        this.discountedMenus = new HashMap<>();
        this.discountedTotalPrice = 0;
    }

    public void discountEachMenu(Menu menu, Integer discountedPrice, Integer count) {
        int calculateDiscount = count * discountedPrice;
        addTotalDiscountPrice(calculateDiscount);
        this.discountedMenus.put(menu, this.discountedMenus.getOrDefault(menu, 0) + calculateDiscount);
    }

    public void discountEachMenu(Menu menu, Integer discountedPrice) {
        discountEachMenu(menu, discountedPrice, 1);
    }

    public void discountTotalPrice(int price) {
        addTotalDiscountPrice(price);
    }

    public void add(DiscountedMenus discountedMenuForAdd) {
        Map<Menu, Integer> discountedPriceForAdd = discountedMenuForAdd.discountedMenus;

        int remainTotalPrice = discountedMenuForAdd.discountedTotalPrice;

        for (Menu menu : discountedPriceForAdd.keySet()) {
            Integer discountedPrice = discountedPriceForAdd.get(menu);
            discountEachMenu(menu, discountedPrice);
            remainTotalPrice -= discountedPrice;
        }

        addTotalDiscountPrice(remainTotalPrice); //할인 총합과 개별 할인금액은 다르기때문에 나머지 금액을 총 할인금액에 더해줌
    }

    public int getDiscountedTotalPrice() {
        return discountedTotalPrice;
    }

    public int getDiscountedPrice(Menu menu) {
        return discountedMenus.get(menu);
    }

    private void addTotalDiscountPrice(int amount) {
        discountedTotalPrice += amount;
    }
}
