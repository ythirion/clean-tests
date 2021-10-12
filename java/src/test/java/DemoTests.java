import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DemoTests {
    @Test
    void should_return_false_for_abc() {
        assertThat(demo.Demo.isLong("abc")).isFalse();
    }
}