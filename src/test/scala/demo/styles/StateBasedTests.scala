package demo.styles

import org.scalatest.flatspec.AnyFlatSpec

class StateBasedTests extends AnyFlatSpec {
  it should "purchase successfully when enough inventory" in {
    val product = Product("Free Guy")

    val sut = new Order().add(product)

    assert(1 == sut.products.size)
    assert(product == sut.products.head)
  }

  final case class Product(name: String)

  final class Order(val products: List[Product] = List[Product]()) {
    def add(product: Product): Order = {
      new Order(products :+ product)
    }
  }
}
