package anti.patterns

import demo.blog.{Article, BlogService, Comment}
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

import java.time.LocalDate

class BlogTests extends AnyFlatSpec with EitherValues {

  it should "returns a Right for valid comment" in {
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = blogService.addComment(article, "Amazing article !!!", "Pablo Escobar")

    assert(result.isRight)
  }

  it should "add a comment with the given text" in {
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val text = "Amazing article !!!"
    val result = blogService.addComment(article, text, "Pablo Escobar")

    assert(result.value.comments.head.text == text)
  }

  it should "add a comment with the given author" in {
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val author = "Pablo Escobar"
    val result = blogService.addComment(article, "Amazing article !!!", author)

    assert(result.value.comments.head.author == author)
  }

  it should "add a comment with the date of the day" in {
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val author = "Pablo Escobar"
    val result = blogService.addComment(article, "Amazing article !!!", author)
  }

  it should "returns a Left when adding existing comment" in {
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      List(Comment("Amazing article !!!", "Pablo Escobar", LocalDate.now()))
    )
    val result = blogService
      .addComment(article, "Amazing article !!!", "Pablo Escobar")

    assert(result.isLeft)
  }
}
