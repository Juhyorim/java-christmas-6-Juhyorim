package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.domain.benefit.EventBadge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ApplicationTest2 extends NsTest {
    public static final String SIMPLE_ORDER_INPUT = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";
    public static final String UNDER_10000_ORDER_INPUT = "타파스-1,제로콜라-1";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String ONLY_MAIN_ORDER_INPUT = "바비큐립-3";
    public static final String ONLY_DESSERT_ORDER_INPUT = "초코케이크-3";

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
                        UNDER_10000_ORDER_INPUT,
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
                        UNDER_10000_ORDER_INPUT,
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

    @Nested
    @DisplayName("혜택 내역 관련")
    class BenefitsTest {
        @ParameterizedTest
        @CsvSource(
                value = {
                        UNDER_10000_ORDER_INPUT,
                        "양송이수프-1,제로콜라-1",
                        "시저샐러드-1",
                        "아이스크림-1,제로콜라-1"
                },
                delimiter = ':'
        )
        @DisplayName("주문 금액이 10000원 미만일 때 아무것도 적용되지 않는 것 확인")
        void totalOrderPriceIsUnder10000(String orderInput) {
            assertSimpleTest(() -> {
                run("25", orderInput);
                assertThat(output()).contains(
                        "<증정 메뉴>" + LINE_SEPARATOR + "없음",
                        "<혜택 내역>" + LINE_SEPARATOR + "없음",
                        "<총혜택 금액>" + LINE_SEPARATOR + "0원",
                        "<12월 이벤트 배지>" + LINE_SEPARATOR + "없음"
                );
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "30:해산물파스타-5,아이스크림-3:크리스마스 디데이 할인", // 크리스마스 디데이 할인 0원
                        "2:초코케이크-5,아이스크림-3:주말 할인", //주말할인 0원
                        "4:해산물파스타-1,티본스테이크-1:평일 할인", //평일할인 0원
                        "18:크리스마스파스타-3,아이스크림-3:특별 할인", //특별 할인 0원
                        "3:초코케이크-5:증정 이벤트" //증정 이벤트 0원
                },
                delimiter = ':'
        )
        @DisplayName("혜택이 0원일 때 포함되지 않는지 확인")
        void dontPrint0WonBenefit(String dayOfMonth, String orderInput, String notContainMessage) {
            assertSimpleTest(() -> {
                run(dayOfMonth, orderInput);
                assertThat(output()).doesNotContain(
                        notContainMessage
                );
            });
        }
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "아이스크림-1:5,000",
                    "타파스-1,제로콜라-1:8,500",
                    "해산물파스타-1,샴페인-1:60,000",
                    "양송이수프-1,바비큐립-2:114,000",
                    "초코케이크-1,아이스크림-1:20,000",
                    "티본스테이크-1,레드와인-19:1,195,000원"
            },
            delimiter = ':'
    )
    @DisplayName("총주문 금액 콤마표시 확인 테스트")
    void 총주문_금액_콤마표시_테스트(String orderInput, String priceWithComma) {
        assertSimpleTest(() -> {
            run("26", orderInput);
            assertThat(output()).contains(priceWithComma);
        });
    }

    @Nested
    @DisplayName("크리스마스 디데이 할인 관련")
    class ChristmasDDayDiscountTest {
        @ParameterizedTest
        @CsvSource(
                value = {
                        "1:1,000",
                        "2:1,100",
                        "3:1,200",
                        "5:1,400",
                        "9:1,800",
                        "10:1,900",
                        "17:2,600",
                        "20:2,900",
                        "21:3,000",
                        "23:3,200",
                        "24:3,300",
                        "25:3,400"
                },
                delimiter = ':'
        )
        @DisplayName("할인금액 테스트")
        void christmasDiscountAmount(String dayOfMonth, String discountAmount) {
            assertSimpleTest(() -> {
                run(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).contains("크리스마스 디데이 할인: -" + discountAmount + "원");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {"26", "27", "28", "29", "30", "31"},
                delimiter = ':'
        )
        @DisplayName("할인 적용안되는 경우 테스트")
        void noChristmasDiscount(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, SIMPLE_ORDER_INPUT);
                assertThat(output()).doesNotContain("크리스마스 디데이 할인");
            });
        }
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "3:true", "10:true", "17:true", "24:true", "25:true", "31:true",
                    "1:false", "2:false", "4:false", "11:false", "23:false", "30:false"
            },
            delimiter = ':'
    )
    @DisplayName("특별할인 적용 테스트")
    void specialDayDiscountTest(String dayOfMonth, boolean isSpecialDay) {
        assertSimpleTest(() -> {
            run(dayOfMonth, SIMPLE_ORDER_INPUT);
            String output = output();
            assertThat(output.contains("특별 할인: -1,000원")).isEqualTo(isSpecialDay);
            assertThat(output.contains("특별 할인")).isEqualTo(isSpecialDay);
        });
    }

    @Nested
    @DisplayName("평일/주말 할인 관련")
    class WeekDiscountTeest {
        @ParameterizedTest
        @CsvSource(
                value = {
                        "3:true", "4:true", "7:true", "10:true", "11:true", "14:true",
                        "17:true", "18:true", "21:true", "24:true", "28:true", "31:true",
                        "1:false", "2:false", "8:false", "9:false", "15:false", "16:false",
                        "22:false", "23:false", "29:false", "30:false"
                },
                delimiter = ':'
        )
        @DisplayName("평일/주말 할인 적용 테스트")
        void weekDayDiscountTest(String dayOfMonth, boolean weekDayDiscountInclude) {
            assertSimpleTest(() -> {
                run(dayOfMonth, SIMPLE_ORDER_INPUT);
                String output = output();
                assertThat(output.contains("평일 할인")).isEqualTo(weekDayDiscountInclude);
                assertThat(output.contains("주말 할인")).isEqualTo(!weekDayDiscountInclude);
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "3", "4", "7", "10", "11", "14",
                        "17", "18", "21", "24", "28", "31"
                },
                delimiter = ':'
        )
        @DisplayName("디저트 없는 평일할인 테스트")
        void noDessertOrderWeekday(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, ONLY_MAIN_ORDER_INPUT);
                assertThat(output()).doesNotContain("평일 할인");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "3", "4", "7", "10", "11", "14",
                        "17", "18", "21", "24", "28", "31"
                },
                delimiter = ':'
        )
        @DisplayName("디저트만 있는 주문 평일할인 테스트")
        void onlyDessertOrderWeekday(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, ONLY_DESSERT_ORDER_INPUT);
                assertThat(output()).contains("평일 할인");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "1", "2", "8", "9", "15", "16",
                        "22", "23", "29", "30"
                },
                delimiter = ':'
        )
        @DisplayName("메인메뉴만 있는 주문 주말 할인 테스트")
        void noDessertOrderWeekend(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, ONLY_MAIN_ORDER_INPUT);
                assertThat(output()).contains("주말 할인");
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "1", "2", "8", "9", "15", "16",
                        "22", "23", "29", "30"
                },
                delimiter = ':'
        )
        @DisplayName("메인메뉴 없는 주말 할인 테스트")
        void noMainOrderWeekend(String dayOfMonth) {
            assertSimpleTest(() -> {
                run(dayOfMonth, ONLY_DESSERT_ORDER_INPUT);
                String output = output();
                assertThat(output).doesNotContain("주말 할인");
            });
        }
    }

    @Nested
    @DisplayName("12월 이벤트 배지 관련")
    class EventBadgeTest {
        private static final String DECEMBER_EVENT_BADGE_TITLE = "<12월 이벤트 배지>";

        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-1,초코케이크-2:STAR", //1300 + 2023*2
                        "타파스-1,초코케이크-5:TREE", //1300 + 2023*5
                        "타파스-1,초코케이크-10:SANTA" //1300 + 2023*10
                },
                delimiter = ':'
        )
        @DisplayName("이벤트 배지 출력 테스트")
        void eventBadgeTest(String orderInput, EventBadge eventBadge) {
            assertSimpleTest(() -> {
                run("4", orderInput);

                assertThat(output()).contains(
                        DECEMBER_EVENT_BADGE_TITLE + LINE_SEPARATOR + eventBadge.getName()
                );
            });
        }

        @ParameterizedTest
        @CsvSource(
                value = {
                        "타파스-1,제로콜라-1:NONE", //0
                        "타파스-1,바비큐립-1:NONE" //1300
                },
                delimiter = ':'
        )
        @DisplayName("이벤트 배지 없음 출력")
        void eventBadgeNone() {
            assertSimpleTest(() -> {
                run("26", UNDER_10000_ORDER_INPUT);
                assertThat(output()).contains(DECEMBER_EVENT_BADGE_TITLE + LINE_SEPARATOR + "없음");
            });
        }
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "1:초코케이크-2:29,000", //30000 - 1000(크리스마스)
                    "31:바비큐립-1:53,000", //54000 - 1000(특별)
                    "3:바비큐립-1:51,800" //54000 - 1200(크리스마스) - 1000(특별) = 51800
            },
            delimiter = ':'
    )
    @DisplayName("총주문 금액 할인 테스트")
    void dontPrint0WonBenefit(String dayOfMonth, String orderInput, String expectedPrice) {
        assertSimpleTest(() -> {
            run(dayOfMonth, orderInput);
            assertThat(output()).contains(
                    "<할인 후 예상 결제 금액>" + LINE_SEPARATOR + expectedPrice
            );
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
