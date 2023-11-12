package christmas.dto;

import christmas.domain.benefit.EventBadge;

public class BenefitResult {
    private BeneficialMenus beneficialMenus;
    private EventBadge eventBadge;

    public BenefitResult(BeneficialMenus beneficialMenus, EventBadge eventBadge) {
        this.beneficialMenus = beneficialMenus;
        this.eventBadge = eventBadge;
    }

    public BeneficialMenus getBeneficialMenus() {
        return beneficialMenus;
    }

    public EventBadge getEventBadge() {
        return eventBadge;
    }
}
