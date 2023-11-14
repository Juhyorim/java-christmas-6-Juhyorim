package christmas.domain.benefit;

public enum EventBadge {
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000),
    NONE("없음",0);

    private String name;
    private Integer minimumPrice;

    private EventBadge(String name, int minimumPrice) {
        this.name = name;
        this.minimumPrice = minimumPrice;
    }

    public static EventBadge getBadge(int totalDiscountPrice) {
        if (totalDiscountPrice < STAR.minimumPrice) {
            return NONE;
        }

        if (totalDiscountPrice < TREE.minimumPrice) {
            return STAR;
        }

        if (totalDiscountPrice < SANTA.minimumPrice) {
            return TREE;
        }

        return SANTA;
    }

    public String getName() {
        return name;
    }
}