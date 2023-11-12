package christmas.dto;

import christmas.domain.Menu;
import christmas.domain.benefit.GiftProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeneficialMenus {
    private Map<Menu, Integer> discountedMenuPrices;
    private List<GiftProduct> giftProducts;
    private int discountedTotalPrice;

    public BeneficialMenus() {
        this.discountedMenuPrices = new HashMap<>();
        this.giftProducts = new ArrayList<>();
        this.discountedTotalPrice = 0;
    }

    public void discountPrice(Menu menu, Integer discountedPrice, Integer count) {
        int calculateDiscount = count * discountedPrice;
        discountedTotalPrice += calculateDiscount;
        this.discountedMenuPrices.put(menu, this.discountedMenuPrices.getOrDefault(menu, 0) + calculateDiscount);
    }

    public void discountPrice(Menu menu, Integer discountedPrice) {
        discountedTotalPrice += discountedPrice;
        this.discountedMenuPrices.put(menu, this.discountedMenuPrices.getOrDefault(menu, 0) + discountedPrice);
    }

    public void discountTotalPrice(int price) {
        this.discountedTotalPrice += price;
    }

    public void add(BeneficialMenus discountedMenuForAdd) {
        Map<Menu, Integer> discountedPriceForAdd = discountedMenuForAdd.discountedMenuPrices;

        for (Menu menu : discountedPriceForAdd.keySet()) {
            Integer discountedPrice = discountedPriceForAdd.get(menu);
            discountPrice(menu, discountedPrice);
        }

        List<GiftProduct> acceptableGiftProducts = discountedMenuForAdd.getGiftProducts();
        if (!acceptableGiftProducts.isEmpty()) {
            this.giftProducts.addAll(acceptableGiftProducts);
        }

        this.discountTotalPrice(discountedMenuForAdd.getDiscountedTotalPrice());
    }

    public int getDiscountedTotalPrice() {
        return discountedTotalPrice;
    }

    public int getDiscountedPrice(Menu menu) {
        return discountedMenuPrices.get(menu);
    }

    public void addGiftProduct(GiftProduct giftProduct) {
        this.giftProducts.add(giftProduct);
        discountedTotalPrice += giftProduct.getPrice();
    }

    public List<GiftProduct> getGiftProducts() {
        return giftProducts;
    }
}
