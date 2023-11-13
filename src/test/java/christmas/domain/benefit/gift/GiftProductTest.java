package christmas.domain.benefit.gift;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiftProductTest {
    @ParameterizedTest
    @CsvSource(
            value = {
                    "5000:true", "10000:true", "20000:true", "119999:true",
                    "120000:false", "120001:false", "130000:false"
            },
            delimiter = ':'
    )
    @DisplayName("주문 금액에 따라 증정상품 지급 확인")
    void findPossibleGift(int totalPrice, boolean canGetGift) {
        GiftCheck giftCheck = new GiftCheck(totalPrice);
        PossibleGift possibleGift = GiftProduct.getPossibleGift(giftCheck);

        assertThat(possibleGift.giftProducts.isEmpty()).isEqualTo(canGetGift);
    }
}