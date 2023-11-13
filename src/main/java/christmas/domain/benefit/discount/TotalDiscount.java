package christmas.domain.benefit.discount;

import java.util.Map;

public class TotalDiscount {
    private Map<DiscountType, Integer> menuAndDiscountPrice;
    private DiscountedMenus discountedMenus;

    public TotalDiscount(Map<DiscountType, Integer> menuAndDiscountPrice, DiscountedMenus discountedMenus) {
        this.menuAndDiscountPrice = menuAndDiscountPrice;
        this.discountedMenus = discountedMenus;
    }

    public Map<DiscountType, Integer> getMenuAndDiscountPrice() {
        return menuAndDiscountPrice;
    }

    public DiscountedMenus getDiscountedMenus() {
        return discountedMenus;
    }

    public int getTotalDiscountPrice() {
        return discountedMenus.getDiscountedTotalPrice();
    }
}
