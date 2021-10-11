package anti.patterns.solution

import demo.blog.Article
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

import java.time.LocalDate

class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val article = new Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
  )

  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"

  it should "add a new comment in the Article including given text / author" in {
    val updatedArticle = article.addComment(text, author)
    assert(updatedArticle.isRight)
    assertAddedComment(updatedArticle.value, text, author)
  }

  it should "add a new comment in an Article containing existing comments" in {
    val newText = "Finibus Bonorum et Malorum"
    val newAuthor = "Al Capone"

    val updatedArticle = article
      .addComment(text, author)
      .map(_.addComment(newText, newAuthor))
      .flatten

    assert(updatedArticle.isRight)
    assert(updatedArticle.value.comments.size == 2)

    assertAddedComment(updatedArticle.value, newText, newAuthor)
  }

  private def assertAddedComment(
      article: Article,
      expectedText: String,
      expectedAuthor: String
  ): Unit = {
    val addedComment = article.comments.head
    assert(addedComment.text == expectedText)
    assert(addedComment.author == expectedAuthor)
    assert(addedComment.creationDate.isEqual(LocalDate.now))
  }

  it should "return an error when adding existing comment" in {
    val updatedArticle = article
      .addComment(text, author)
      .map(_.addComment(text, author))
      .flatten

    assert(updatedArticle.isLeft)
    assert(updatedArticle.left.value.size == 1)
    assert(
      updatedArticle.left.value.head.description == "Comment already in the article"
    )
  }
}
