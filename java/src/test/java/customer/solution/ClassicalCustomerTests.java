package customer.solution;

import customer.CustomerService;
import customer.ProductType;
import customer.Store;
import io.vavr.collection.HashMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClassicalCustomerTests {
    private final Store store = new Store(HashMap.empty())
            .addInventory(ProductType.BOOK, 10);

    @Test
    void it_should_purchase_successfully_when_enough_inventory() {
        final var updatedStore = CustomerService.purchase(store, ProductType.BOOK, 6);
        assertThat(updatedStore.isSuccess()).isTrue();
        assertThat(updatedStore.get().getInventoryFor(ProductType.BOOK)).isEqualTo(4);
    }

    @Test
    void it_should_fail_when_not_enough_inventory() {
        final var updatedStore = CustomerService.purchase(store, ProductType.BOOK, 11);
        assertThat(updatedStore.isFailure()).isTrue();
        assertThat(updatedStore.getCause())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough inventory");
    }
}
