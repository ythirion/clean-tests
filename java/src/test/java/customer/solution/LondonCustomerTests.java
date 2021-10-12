package customer.solution;

import customer.CustomerService;
import customer.ProductType;
import customer.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LondonCustomerTests {
    private final int QUANTITY = 6;

    @Mock
    private Store storeMock;

    @Test
    void it_should_purchase_successfully_when_enough_inventory() {
        when(storeMock.hasEnoughInventory(ProductType.BOOK, QUANTITY)).thenReturn(true);
        final var updatedStore = CustomerService.purchase(storeMock, ProductType.BOOK, 6);

        assertThat(updatedStore.isSuccess()).isTrue();
        verify(storeMock, times(1)).removeInventory(ProductType.BOOK, QUANTITY);
    }

    @Test
    void it_should_fail_when_not_enough_inventory() {
        final var updatedStore = CustomerService.purchase(storeMock, ProductType.BOOK, 11);
        assertThat(updatedStore.isFailure()).isTrue();
        assertThat(updatedStore.getCause())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough inventory");
        verify(storeMock, never()).removeInventory(ProductType.BOOK, QUANTITY);
    }
}