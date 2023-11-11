package christmas.domain.benefit;

public enum EventBadge {
    STAR,
    TREE,
    SANTA,
    NONE;

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
}