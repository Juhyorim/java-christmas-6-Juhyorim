package christmas.domain.benefit.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PossibleGiftTest {
    @Test
    @DisplayName("증정상품 가격 총합 계산")
    void giftTotalPrice() {
        PossibleGift possibleGift = new PossibleGift(List.of(GiftProduct.CHAMPAGNE, GiftProduct.CHAMPAGNE));
        assertThat(possibleGift.getTotalPrice()).isEqualTo(GiftProduct.CHAMPAGNE.getPrice() * 2);
    }
}