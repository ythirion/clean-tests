package styles;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StateBasedTests {
    @Test
    void it_should_add_given_product_to_the_order() {
        val product = new Product("Free Guy");

        val sut = new Order().add(product);

        // Verify the state
        assertThat(sut.getProducts())
                .hasSize(1)
                .allMatch(item -> item.equals(product));
    }

    @AllArgsConstructor
    class Product {
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    class Order {
        private final List<Product> products;

        Order() {
            products = List.empty();
        }

        Order add(Product product) {
            return new Order(products.append(product));
        }
    }
}
