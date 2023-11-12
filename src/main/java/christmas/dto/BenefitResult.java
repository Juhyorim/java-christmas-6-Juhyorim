package christmas.dto;

import christmas.domain.benefit.EventBadge;
import christmas.domain.benefit.TotalBenefit;

public class BenefitResult {
    private TotalBenefit totalBenefit;
    private EventBadge eventBadge;

    public BenefitResult(TotalBenefit totalBenefit, EventBadge eventBadge) {
        this.totalBenefit = totalBenefit;
        this.eventBadge = eventBadge;
    }

    public TotalBenefit getTotalBenefit() {
        return totalBenefit;
    }

    public EventBadge getEventBadge() {
        return eventBadge;
    }
}
