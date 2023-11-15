package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ApplicationTest2 extends NsTest {
    public static final String SIMPLE_ORDER_INPUT = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";

    @Test
    @DisplayName("모든 문구 출력 테스트")
    void printAllText() {
        assertSimpleTest(() -> {
            run("3", SIMPLE_ORDER_INPUT);
            assertThat(output()).contains(
                    "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.",
                    "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)",
                    "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)",
                    "12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
            );
        });
    }

    @Nested
    @DisplayName("주문날짜 입력 관련")
    class OrderDate {
        private static final String INVALID_ORDER_DATE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
        private static final String DAY_FORMAT = "12월 %s일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

        @ParameterizedTest
        @CsvSource(value = {"1", "2", "3", "7", "13", "17", "23", "31"})
        @DisplayName("출력 테스트")
        void printDayOfMonth(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).contains(DAY_FORMAT.formatted(dayOfMonth));
            });
        }

        @ParameterizedTest
        @CsvSource(value = {"1 ", " 2", " 3 ", "   7   "})
        @DisplayName("공백포함 테스트")
        void printDayOfMonthWithBlank(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).contains(DAY_FORMAT.formatted(dayOfMonth));
            });
        }

        @ParameterizedTest
        @CsvSource(value = {"0", "32", "33", "34"})
        @DisplayName("범위 밖의 값 테스트")
        void notInEventPeriod(String dayOfMonth) {
            assertSimpleTest(() -> {
                runException(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).contains(INVALID_ORDER_DATE);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {"ㄱ", "ㄴ", "r", "_"})
        @DisplayName("숫자가 아닐 때 테스트")
        void notNumeric(String dayOfMonth) {
            assertSimpleTest(() -> {
                runException(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).contains(INVALID_ORDER_DATE);
            });
        }
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
