package christmas.domain.benefit;

import christmas.domain.Menu;

public enum GiftProduct {
    CHAMPAGNE(Menu.CHAMPAGNE.getName(), Menu.CHAMPAGNE.getPrice());

    String name;
    int price;

    GiftProduct(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
