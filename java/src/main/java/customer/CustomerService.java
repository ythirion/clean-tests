package customer;

import io.vavr.collection.Map;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import static io.vavr.API.Failure;
import static io.vavr.API.Success;

enum ProductType {
    Bluray,
    Book
}

@AllArgsConstructor
class Store {
    private final Map<ProductType, Integer> inventory;

    public Store removeInventory(ProductType product, Integer quantity) {
        return updateStore(product, getInventoryFor(product) - quantity);
    }

    public Store addInventory(ProductType product, Integer quantity) {
        return updateStore(product, getInventoryFor(product) + quantity);
    }

    public boolean hasEnoughInventory(ProductType product, Integer quantity) {
        return getInventoryFor(product) >= quantity;
    }

    private Integer getInventoryFor(ProductType product) {
        return inventory.getOrElse(product, 0);
    }

    private Store updateStore(ProductType product, Integer newQuantity) {
        return new Store(inventory.put(product, newQuantity));
    }
}

public class CustomerService {
    public Try<Store> purchase(
            Store store,
            ProductType product,
            Integer quantity
    ) {
        return (!store.hasEnoughInventory(product, quantity)) ?
                Failure(new IllegalArgumentException("Not enough inventory")) :
                Success(store.removeInventory(product, quantity));
    }
}
