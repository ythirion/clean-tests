package anti.patterns

import demo.blog.Article
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

class BlogTests extends AnyFlatSpec with EitherValues {

  it should "returns a Right for valid comment" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = article.addComment("Amazing article !!!", "Pablo Escobar")

    assert(result.isRight)
  }

  it should "add a comment with the given text" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val text = "Amazing article !!!"
    val result = article.addComment(text, "Pablo Escobar")

    assert(result.value.comments.head.text == text)
  }

  it should "add a comment with the given author" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val author = "Pablo Escobar"
    val result = article.addComment("Amazing article !!!", author)

    assert(result.value.comments.head.author == author)
  }

  it should "add a comment with the date of the day" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val author = "Pablo Escobar"
    val result = article.addComment("Amazing article !!!", author)
  }

  it should "returns a Left when adding existing comment" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = article
      .addComment("Amazing article !!!", "Pablo Escobar")
      .map(_.addComment("Amazing article !!!", "Pablo Escobar"))
      .flatten

    assert(result.isLeft)
  }
}
