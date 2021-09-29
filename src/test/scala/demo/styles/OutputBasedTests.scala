package demo.styles

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

final case class Product(name: String)

object PriceEngine {
  def calculateDiscount(products: Product*): Double = {
    val discount = products.length * 0.01
    Math.min(discount, 0.2)
  }
}
