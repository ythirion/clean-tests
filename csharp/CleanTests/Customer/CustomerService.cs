using LanguageExt;

namespace CleanTests.Customer;

public static class CustomerService
{
    public static Try<Store> Purchase(
        Store store,
        ProductType product,
        int quantity)
        => () => !store.HasEnoughInventory(product, quantity)
            ? throw new ArgumentException("Not enough inventory")
            : store.RemoveInventory(product, quantity);
}