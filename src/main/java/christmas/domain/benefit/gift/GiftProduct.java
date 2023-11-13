package christmas.domain.benefit.gift;

import christmas.domain.menu.Menu;
import christmas.domain.benefit.BenefitVerification;
import christmas.dto.BenefitCheck;
import christmas.dto.GiftCheck;

public enum GiftProduct {
    CHAMPAGNE(Menu.CHAMPAGNE.getName(), (benefitCheck) -> canGetChampagne(benefitCheck), Menu.CHAMPAGNE.getPrice(),
            (giftcheck) -> calculateChampagneCount(giftcheck));

    String name;

    BenefitVerification verify;
    int price;
    GiftCount giftCount;

    GiftProduct(String name, BenefitVerification verify, int price, GiftCount giftCount) {
        this.name = name;
        this.verify = verify;
        this.price = price;
        this.giftCount = giftCount;
    }

    private static boolean canGetChampagne(BenefitCheck benefitCheck) {
        GiftCheck giftCheck = (GiftCheck) benefitCheck;
        if (giftCheck.getDiscountedTotalPrice() < 120_000) {
            return false;
        }

        return true;
    }

    private static int calculateChampagneCount(GiftCheck giftCheck) {
        if (giftCheck.getDiscountedTotalPrice() < 120_000) {
            return 0;
        }

        return 1;
    }

    public static PossibleGift getPossibleGift(GiftCheck giftCheck) {
        PossibleGift possibleGift = new PossibleGift();

        for (GiftProduct giftProduct : values()) {
            if (giftProduct.verify.check(giftCheck)) {
                possibleGift.add(giftProduct, giftProduct.giftCount.count(giftCheck));
            }
        }

        return possibleGift;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
