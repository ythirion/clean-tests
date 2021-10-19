package demo.customer

final case class Product(name: String)

object PriceEngine {
  private val maxDiscount: Double = 0.2
  private val ratio: Double = 0.01

  def calculateDiscount(products: Product*): Double = {
    val discount = products.length * ratio
    Math.min(discount, maxDiscount)
  }
}
