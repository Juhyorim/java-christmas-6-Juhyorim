package christmas.domain.benefit;

public enum EventBadge {
    STAR("별"),
    TREE("트리"),
    SANTA("산타"),
    NONE("없음");

    String name;

    EventBadge(String name) {
        this.name = name;
    }

    public static EventBadge getBadge(int totalDiscountPrice) {
        if (totalDiscountPrice < 5_000) {
            return NONE;
        }

        if (totalDiscountPrice < 10_000) {
            return STAR;
        }

        if (totalDiscountPrice < 20_000) {
            return TREE;
        }

        return SANTA;
    }

    public String getName() {
        return name;
    }
}