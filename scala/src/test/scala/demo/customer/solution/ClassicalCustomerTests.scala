package demo.customer.solution

import demo.customer.{CustomerService, ProductType, Store}
import org.scalatest.TryValues.convertTryToSuccessOrFailure
import org.scalatest.flatspec.AnyFlatSpec

class ClassicalCustomerTests extends AnyFlatSpec {
  val store: Store = new Store()
    .addInventory(ProductType.Book, 10)

  val customerService: CustomerService = new CustomerService()

  it should "purchase successfully when enough inventory" in {
    val updatedStore = customerService.purchase(store, ProductType.Book, 6)
    assert(updatedStore.success.value.getInventoryFor(ProductType.Book) == 4)
  }

  it should "fail to purchase when not enough inventory" in {
    val updatedStore = customerService.purchase(store, ProductType.Book, 11)
    assert(updatedStore.isFailure)
  }
}
