package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SplitManagerTest {
    @Test
    @DisplayName("콤마로 split 테스트")
    void splitCommaTest() {
        String input = "안녕, 하세요,저는, 주효림 ,입니다,";
        List<String> split = SplitManager.split(input, ',');
        assertThat(split).containsExactly("안녕", "하세요", "저는", "주효림", "입니다", "");
    }
}