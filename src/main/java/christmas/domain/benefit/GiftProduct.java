package christmas.domain.benefit;

import christmas.domain.Menu;

public enum GiftProduct {
    CHAMPAGNE(Menu.CHAMPAGNE.getPrice());

    int price;

    GiftProduct(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
