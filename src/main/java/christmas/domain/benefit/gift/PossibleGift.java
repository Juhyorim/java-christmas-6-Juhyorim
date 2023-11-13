package christmas.domain.benefit.gift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PossibleGift {
    Map<GiftProduct, Integer> giftProductCount = new HashMap<>();

    public PossibleGift() {
    }

    public void add(GiftProduct giftProduct, int count) {
        giftProductCount.put(giftProduct, giftProductCount.getOrDefault(giftProduct, 0) + count);
    }

    public int getGiftCount() {
        return giftProductCount.size();
    }

    public List<GiftProduct> getGiftProductsTypes() {
        return new ArrayList<>(giftProductCount.keySet());
    }

    public int getCount(GiftProduct giftProduct) {
        return giftProductCount.get(giftProduct);
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (GiftProduct giftProduct : giftProductCount.keySet()) {
            totalPrice += (giftProduct.getPrice() * giftProductCount.get(giftProduct));
        }

        return totalPrice;
    }
}
