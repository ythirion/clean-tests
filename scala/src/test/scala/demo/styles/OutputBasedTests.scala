package demo.styles

import demo.customer.{PriceEngine, Product}
import org.scalatest.flatspec.AnyFlatSpec

class OutputBasedTests extends AnyFlatSpec {
  it should "calculate the discount of 2 products" in {
    val product1 = Product("Kaamelott")
    val product2 = Product("Free Guy")

    // Call on the SUT (here PriceEngine)
    // No side effects -> Pure function
    val discount = PriceEngine.calculateDiscount(product1, product2)

    assert(0.02 == discount)
  }
}
