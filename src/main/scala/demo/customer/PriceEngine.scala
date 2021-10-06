package demo.customer

final case class Product(name: String)

object PriceEngine {
  def calculateDiscount(products: Product*): Double = {
    val discount = products.length * 0.01
    Math.min(discount, 0.2)
  }
}
