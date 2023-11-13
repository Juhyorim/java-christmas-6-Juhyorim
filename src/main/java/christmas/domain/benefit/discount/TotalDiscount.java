package christmas.domain.benefit.discount;

import java.util.Map;

public class TotalDiscount {
    private Map<DiscountType, Integer> discountPriceByDiscountType;
    private DiscountedMenus discountedMenus;

    public TotalDiscount(Map<DiscountType, Integer> menuAndDiscountPrice, DiscountedMenus discountedMenus) {
        this.discountPriceByDiscountType = menuAndDiscountPrice;
        this.discountedMenus = discountedMenus;
    }

    public Map<DiscountType, Integer> getgetDiscountPriceByDiscountType() {
        return discountPriceByDiscountType;
    }

    public DiscountedMenus getDiscountedMenus() {
        return discountedMenus;
    }

    public int getTotalDiscountPrice() {
        return discountedMenus.getDiscountedTotalPrice();
    }
}
