package styles;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.junit.jupiter.api.Test;

import static java.lang.Math.min;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OutputBasedTests {
    @Test
    void discount_of_2_products_should_be_2_percent() {
        val product1 = new Product("Kaamelott");
        val product2 = new Product("Free Guy");

        // Call on the SUT (here PriceEngine)
        // No side effects -> Pure function
        val discount = PriceEngine.calculateDiscount(product1, product2);

        assertThat(discount).isEqualTo(0.02);
    }

    @UtilityClass
    public class PriceEngine {
        public static Double calculateDiscount(Product... products) {
            val discount = products.length * 0.01;
            return min(discount, 0.2);
        }
    }

    @AllArgsConstructor
    class Product {
        private final String name;
    }
}