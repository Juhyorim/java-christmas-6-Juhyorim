package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
