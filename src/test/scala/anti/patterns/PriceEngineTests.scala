package anti.patterns

import demo.customer.{PriceEngine, Product}
import org.scalatest.flatspec.AnyFlatSpec

class PriceEngineTests extends AnyFlatSpec {
  it should "calculate the discount of 2 products" in {
    val products = List(Product("P1"), Product("P2"), Product("P3"))

    val discount = PriceEngine.calculateDiscount(products: _*)

    assert(products.length * 0.01 == discount)
  }
}
