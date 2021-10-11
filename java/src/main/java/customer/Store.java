package customer;

import io.vavr.collection.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Store {
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

    public Integer getInventoryFor(ProductType product) {
        return inventory.getOrElse(product, 0);
    }

    private Store updateStore(ProductType product, Integer newQuantity) {
        return new Store(inventory.put(product, newQuantity));
    }
}
