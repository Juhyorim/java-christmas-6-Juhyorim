package christmas.domain.benefit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventBadgeTest {
    @ParameterizedTest
    @CsvSource(
            value = {
                    "1000:NONE", "4999:NONE", "5000:STAR", "5001:STAR", "9999:STAR",
                    "10000:TREE", "19999:TREE", "20000:SANTA", "20001:SANTA"
            },
            delimiter = ':'
    )
    @DisplayName("헤택 가격에 맞는 배지 반환 테스트")
    void getBadge(int benefitPrice, EventBadge eventBadge) {
        assertThat(EventBadge.createBadge(benefitPrice)).isEqualTo(eventBadge);
    }
}