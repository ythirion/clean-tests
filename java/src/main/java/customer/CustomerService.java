package customer;

import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import static io.vavr.API.Failure;
import static io.vavr.API.Success;

@UtilityClass
public class CustomerService {
    public static Try<Store> purchase(
            Store store,
            ProductType product,
            Integer quantity
    ) {
        return (!store.hasEnoughInventory(product, quantity)) ?
                Failure(new IllegalArgumentException("Not enough inventory")) :
                Success(store.removeInventory(product, quantity));
    }
}
