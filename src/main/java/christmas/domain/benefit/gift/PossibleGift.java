package christmas.domain.benefit.gift;

import java.util.List;

public class PossibleGift {
    List<GiftProduct> giftProducts;

    public PossibleGift(List<GiftProduct> giftProducts) {
        this.giftProducts = giftProducts;
    }

    public List<GiftProduct> getGiftProducts() {
        return giftProducts;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (GiftProduct giftProduct : giftProducts) {
            totalPrice += giftProduct.getPrice();
        }

        return totalPrice;
    }
}
