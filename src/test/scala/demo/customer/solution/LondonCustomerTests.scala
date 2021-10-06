package demo.customer.solution

import demo.customer.ProductType.ProductType
import demo.customer.{CustomerService, ProductType, Store}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

class LondonCustomerTests extends AnyFlatSpec with MockFactory {
  val productType: ProductType = ProductType.Book
  val quantity = 6
  val storeStub: Store = stub[Store]

  it should "purchase successfully when enough inventory" in {
    (storeStub.hasEnoughInventory _)
      .when(productType, quantity)
      .returns(true)

    CustomerService.purchase(storeStub, productType, quantity)

    (storeStub.removeInventory _).verify(productType, quantity).once()
  }

  it should "fail to purchase when not enough inventory" in {
    (storeStub.hasEnoughInventory _)
      .when(productType, quantity)
      .returns(false)

    CustomerService.purchase(storeStub, productType, quantity)

    (storeStub.removeInventory _).verify(productType, quantity).never()
  }
}
