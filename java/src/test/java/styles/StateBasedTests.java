package styles;

import lombok.AllArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StateBasedTests {
    @Test
    void it_should_add_given_product_to_the_order() {
        val product = new Product("Free Guy");
        val sut = new Order();

        sut.add(product);

        // Verify the state
        assertThat(sut.getProducts())
                .hasSize(1)
                .allMatch(item -> item.equals(product));
    }

    @AllArgsConstructor
    class Product {
        private final String name;
    }

    class Order {
        private final List<Product> products = new ArrayList<>();

        List<Product> getProducts() {
            return Collections.unmodifiableList(products);
        }

        void add(Product product) {
            products.add(product);
        }
    }
}
