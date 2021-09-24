package demo.customer

import demo.customer.ProductType.ProductType

import scala.util.{Failure, Success, Try}

object ProductType extends Enumeration {
  type ProductType = Value
  val Book, Bluray = Value
}

final class Store(
    private val inventory: Map[ProductType, Int] = Map.empty[ProductType, Int]
) {
  def removeInventory(product: ProductType, quantity: Int): Try[Store] = {
    if (!hasEnoughInventory(product, quantity))
      Failure(new IllegalArgumentException("Not enough inventory"))
    else Success(updateStore(product, inventory(product) - quantity))
  }

  def hasEnoughInventory(product: ProductType, quantity: Int): Boolean =
    getInventoryFor(product) >= quantity

  def getInventoryFor(product: ProductType): Int =
    inventory.getOrElse(product, 0)

  def addInventory(product: ProductType, quantity: Int): Store =
    updateStore(product, inventory.getOrElse(product, 0) + quantity)

  private def updateStore(product: ProductType, newQuantity: Int): Store =
    new Store(inventory + (product -> newQuantity))
}

object CustomerService {
  def purchase(
      store: Store,
      product: ProductType.Value,
      quantity: Int
  ): Try[Store] =
    store.removeInventory(product, quantity)
}
