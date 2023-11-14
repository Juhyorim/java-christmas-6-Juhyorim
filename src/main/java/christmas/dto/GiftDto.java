package christmas.dto;

import christmas.domain.benefit.gift.GiftProduct;
import christmas.domain.benefit.gift.PossibleGift;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftDto {
    Map<String, Integer> giftProducts = new HashMap<>();

    private GiftDto() {
    }

    public static GiftDto convert(PossibleGift possibleGift) {
        GiftDto giftDto = new GiftDto();
        for (GiftProduct giftProduct : possibleGift.getGiftProductsTypes()) {
            giftDto.giftProducts.put(giftProduct.getName(), possibleGift.getCount(giftProduct));
        }

        return giftDto;
    }

    public int getGiftCount() {
        return giftProducts.size();
    }

    public List<String> getGiftProductsTypes() {
        return new ArrayList<>(giftProducts.keySet());
    }

    public Integer getCount(String giftProductName) {
        return giftProducts.get(giftProductName);
    }
}
