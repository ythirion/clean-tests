package anti.patterns

import demo.blog.Article
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

import java.time.LocalDate

class BlogTestsRefactored extends AnyFlatSpec with EitherValues {
  private val article = new Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
  )

  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"

  it should "add a new comment in the Article including given text / author" in {
    val result = article.addComment(text, author)

    assert(result.isRight)

    val addedComment = result.value.comments.head
    assert(addedComment.text == text)
    assert(addedComment.author == author)
    assert(addedComment.creationDate.isEqual(LocalDate.now))
  }

  it should "add a new comment in an Article containing existingh comments" in {
    val newText = "Finibus Bonorum et Malorum"
    val newAuthor = "Al Capone"

    val result = article
      .addComment(text, author)
      .map(_.addComment(newText, newAuthor))
      .flatten

    assert(result.isRight)
    assert(result.value.comments.size == 2)

    val addedComment = result.value.comments.head
    assert(addedComment.text == newText)
    assert(addedComment.author == newAuthor)
    assert(addedComment.creationDate.isEqual(LocalDate.now))
  }

  it should "return an error when adding existing comment" in {
    val result = article
      .addComment(text, author)
      .map(_.addComment(text, author))
      .flatten

    assert(result.isLeft)
    assert(result.left.value.size == 1)
    assert(
      result.left.value.head.description == "Comment already in the article"
    )
  }
}
