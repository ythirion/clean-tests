package demo.customer

import org.scalatest.TryValues.convertTryToSuccessOrFailure
import org.scalatest.flatspec.AnyFlatSpec

class ClassicalCustomerTests extends AnyFlatSpec {
  it should "purchase successfully when enough inventory" in {
    // Arrange
    val store = new Store()
      .addInventory(ProductType.Book, 10)

    // Act
    val updatedStore = CustomerService.purchase(store, ProductType.Book, 6)

    // Assert
    assert(updatedStore.success.value.getInventoryFor(ProductType.Book) == 4)
  }

  it should "fail to purchase when not enough inventory" in {
    // Arrange
    val store = new Store()
      .addInventory(ProductType.Book, 1)

    // Act
    val updatedStore = CustomerService.purchase(store, ProductType.Book, 6)

    // Assert
    assert(updatedStore.isFailure)
  }
}
