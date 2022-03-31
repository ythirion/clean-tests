using LanguageExt;

namespace CleanTests.Customer;

public sealed record Store(Map<ProductType, int> Inventory)
{
    public Store()
        : this(Map<ProductType, int>.Empty)
    {
    }

    public Store AddInventory(ProductType product, int quantity)
        => UpdateStore(product, GetInventoryFor(product) + quantity);

    public Store RemoveInventory(ProductType product, int quantity)
        => UpdateStore(product, GetInventoryFor(product) + quantity);

    public bool HasEnoughInventory(ProductType product, int quantity)
        => GetInventoryFor(product) >= quantity;

    private int GetInventoryFor(ProductType product)
        => Inventory[product];

    private Store UpdateStore(ProductType product, int newQuantity)
        => new(Inventory.AddOrUpdate(product, newQuantity));
}