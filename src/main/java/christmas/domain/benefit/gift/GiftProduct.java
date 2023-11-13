package christmas.domain.benefit.gift;

import christmas.domain.Menu;
import christmas.domain.benefit.BenefitVerification;
import christmas.domain.benefit.BenefitCheck;
import java.util.ArrayList;
import java.util.List;

public enum GiftProduct {
    CHAMPAGNE(Menu.CHAMPAGNE.getName(), (benefitCheck) -> canGetChampagne(benefitCheck), Menu.CHAMPAGNE.getPrice());

    String name;
    BenefitVerification verify;
    int price;

    GiftProduct(String name, BenefitVerification verify, int price) {
        this.name = name;
        this.verify = verify;
        this.price = price;
    }

    private static boolean canGetChampagne(BenefitCheck benefitCheck) {
        GiftCheck giftCheck = (GiftCheck) benefitCheck;
        if (giftCheck.getDiscountedTotalPrice() < 120_000) {
            return false;
        }

        return true;
    }

    public static PossibleGift getPossibleGift(GiftCheck giftCheck) {
        List<GiftProduct> gifts = new ArrayList<>();

        for (GiftProduct giftProduct : values()) {
            if (giftProduct.verify.check(giftCheck)) {
                gifts.add(giftProduct);
            }
        }

        return new PossibleGift(gifts);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
