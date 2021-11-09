package antipatterns.solution;

import customer.PriceEngine;
import customer.Product;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PriceEngineRefactoredTests {
    @Test
    void discount_of_3_products_should_be_3_percent() {
        val products = List.of(new Product("P1"), new Product("P2"), new Product("P3"));
        val discount = PriceEngine.calculateDiscount(products.toArray(new Product[0]));

        assertThat(discount).isEqualTo(0.03);
    }
}
