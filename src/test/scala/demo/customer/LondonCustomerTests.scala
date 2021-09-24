package demo.customer

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

class LondonCustomerTests extends AnyFlatSpec with MockFactory {
  it should "purchase successfully when enough inventory" in {
    // Arrange
    val productType = ProductType.Book
    val quantity = 6
    val storeStub = stub[Store]

    (storeStub.hasEnoughInventory _)
      .when(productType, quantity)
      .returns(true)

    // Act
    val updatedStore =
      CustomerService.purchase(storeStub, productType, quantity)

    // Assert
    (storeStub.removeInventory _).verify(productType, quantity).once()
  }

  it should "fail to purchase when not enough inventory" in {
    // Arrange
    val productType = ProductType.Book
    val quantity = 6
    val storeStub = stub[Store]

    (storeStub.hasEnoughInventory _)
      .when(productType, quantity)
      .returns(false)

    // Act
    val updatedStore =
      CustomerService.purchase(storeStub, productType, quantity)

    // Assert
    (storeStub.removeInventory _).verify(productType, quantity).never()
  }
}
