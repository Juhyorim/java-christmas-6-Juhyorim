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
    private static final String LINE_SEPARATOR = System.lineSeparator();

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
    class OrderDateTest {
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

    @Nested
    @DisplayName("주문 입력 관련")
    class OrderTest {
        private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
        private static final String ONLY_DRINK_ORDER_NOT_ALLOWED = "[ERROR] 음료만 주문이 불가능합니다";

        @ParameterizedTest
        @CsvSource(
                value = {
                        "티본스테이크- 1,바비큐립 -1,초코케이크 - 2,제로콜라  -  3",
                        "티본스테이크-1 , 바비큐립-1, 초코케이크-2 ,   제로콜라-3   ",
                        " 티본스테이크 - 1 , 바비큐립 - 1 , 초코케이크  -  2  ,   제로콜라   -   3   "
                },
                delimiter = ':'
        )
        @DisplayName("공백포함 테스트")
        void orderWithBlank(String orderInput) {
            assertSimpleTest(() -> {
                run("26", orderInput);
                assertThat(output()).contains("티본스테이크 1개", "바비큐립 1개", "초코케이크 2개", "제로콜라 3개");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "-",
                        "레드와인-1-2",
                        "레드와인-레드와인-2",
                        "레드와인-레드와인-2-2",
                        "레드와인,1,제로콜라,2",
                        "레드와인,1-제로콜라,1-샴페인,2",
                        "레드와인-2,제로콜라-3,샴페인"
                },
                delimiter = ':'
        )
        @DisplayName("잘못된 형식 주문 불가능 테스트")
        void notGoodOrderInputException(String orderInput) {
            assertSimpleTest(() -> {
                runException("25", orderInput);
                assertThat(output()).contains(INVALID_ORDER_MESSAGE);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "-",
                        "화이트와인-1",
                        "레드와인-2, 오렌지주스-2",
                        "핫도그-2",
                        "초코케이크-1,티본스테이크-1,팹시콜라-1"
                },
                delimiter = ':'
        )
        @DisplayName("메뉴판에 없는 메뉴 주문 테스트")
        void notInMenu(String orderInput) {
            assertSimpleTest(() -> {
                runException("25", orderInput);
                assertThat(output()).contains(INVALID_ORDER_MESSAGE);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "레드와인-0",
                        "제로콜라-한개",
                        "샴페인-one",
                        "레드와인-1,제로콜라-0,샴페인-2"
                },
                delimiter = ':'
        )
        @DisplayName("개수 이상한 값 테스트")
        void numberIsInvalid(String orderInput) {
            assertSimpleTest(() -> {
                runException("25", orderInput);
                assertThat(output()).contains(INVALID_ORDER_MESSAGE);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-1,타파스-1",
                        "해산물파스타-1,샴페인-2해산물파스타-1,",
                        "양송이수프-1,바비큐립-1,바비큐립-1"
                },
                delimiter = ':'
        )
        @DisplayName("중복 메뉴 주문 예외발생 테스트")
        void duplicatedMenuOrderThrowException(String orderInput) {
            assertSimpleTest(() -> {
                runException("3", orderInput);
                assertThat(output()).contains(INVALID_ORDER_MESSAGE);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-21,제로콜라-1",
                        "해산물파스타-19,샴페인-2",
                        "양송이수프-1,바비큐립-22",
                        "시저샐러드-6,아이스크림-15"
                },
                delimiter = ':'
        )
        @DisplayName("주문 개수가 20개를 초과할 때 테스트")
        void orderNumberOver20(String orderInput) {
            assertSimpleTest(() -> {
                runException("3", orderInput);
                assertThat(output()).contains(INVALID_ORDER_MESSAGE);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "레드와인-1",
                        "제로콜라-2",
                        "샴페인-3",
                        "레드와인-1,제로콜라-1,샴페인-2",
                        "레드와인-2,제로콜라-3,샴페인-1"
                },
                delimiter = ':'
        )
        @DisplayName("음료만 주문 불가능 테스트")
        void orderOnlyDrinkThrowException(String orderInput) {
            assertSimpleTest(() -> {
                runException("25", orderInput);
                assertThat(output()).contains(ONLY_DRINK_ORDER_NOT_ALLOWED);
            });
        }
    }

    @Nested
    @DisplayName("증정메뉴 관련")
    class GiftTest {
        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-1,제로콜라-1",
                        "바비큐립-2",
                        "크리스마스파스타-4"
                },
                delimiter = ':'
        )
        @DisplayName("증정 메뉴 없음 출력")
        void noGift(String orderInput) {
            assertSimpleTest(() -> {
                run("25", orderInput);
                assertThat(output()).contains("<증정 메뉴>" + LINE_SEPARATOR + "없음");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "크리스마스파스타-4,초코케이크-1,아이스크림-1", //12만원
                        "양송이수프-1,바비큐립-1,레드와인-1", //12만원
                        "바비큐립-3" //12만원 이상
                },
                delimiter = ':'
        )
        @DisplayName("12만원이상 주문시 샴페인 증정")
        void getChampagne(String orderInput) {
            assertSimpleTest(() -> {
                run("25", orderInput);
                assertThat(output()).contains("<증정 메뉴>" + LINE_SEPARATOR + "샴페인 1개");
            });
        }
    }

    @Nested
    @DisplayName("총 혜택금액 관련")
    class totalBenefitAmountTest {
        @ParameterizedTest
        @CsvSource(
                value = {
                        "해산물파스타-1,샴페인-1:-4,400",
                        "양송이수프-1,바비큐립-2,해산물파스타-1:-29,400",
                        "바비큐립-1,티본스테이크-1,아이스크림-1:-6,423"
                },
                delimiter = ':'
        )
        @DisplayName("총혜택 금액 마이너스 출력")
        void totalBenefitAmountMinusInclude(String orderInput, String benefitPriceWithMinus) {
            assertSimpleTest(() -> {
                run("25", orderInput);
                assertThat(output()).contains(benefitPriceWithMinus);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-1,제로콜라-1",
                        "양송이수프-1,제로콜라-1",
                        "초코케이크-3"
                },
                delimiter = ':'
        )
        @DisplayName("총혜택 금액 0원 출력")
        void totalBenefitAmountZero(String orderInput) {
            assertSimpleTest(() -> {
                run("29", orderInput);

                String output = output();
                assertThat(output).contains("0원");
                assertThat(output).doesNotContain("-0원");
            });
        }
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
