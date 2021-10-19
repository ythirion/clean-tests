package anti.patterns.solution

import anti.patterns.solution.BlogRefactoredTests._
import demo.blog.{Article, BlogService, Comment}
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

import java.time.LocalDate

class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val blogService = new BlogService()

  it should "add a new comment in an empty Article including given text / author" in {
    val updatedArticle = blogService.addComment(emptyArticle, text, author)
    assert(updatedArticle.isRight)
    assertAddedComment(updatedArticle.value, text, author)
  }

  it should "add a new comment in an Article containing existing comments" in {
    val newText = "Finibus Bonorum et Malorum"
    val newAuthor = "Al Capone"

    val updatedArticle = blogService.addComment(articleWith1Comment, newText, newAuthor)

    assert(updatedArticle.isRight)
    assert(updatedArticle.value.comments.size == 2)
    assertAddedComment(updatedArticle.value, newText, newAuthor)
  }

  it should "return an error when adding existing comment" in {
    val updatedArticle = blogService.addComment(articleWith1Comment, text, author)

    assert(updatedArticle.isLeft)
    assert(updatedArticle.left.value.size == 1)
    assert(updatedArticle.left.value.head.description == "Comment already in the article")
  }
}

object BlogRefactoredTests {
  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"

  private val emptyArticle: Article = articleWithComments(List.empty[Comment])
  private val articleWith1Comment: Article = articleWithComments(List(Comment(text, author, LocalDate.now())))

  private def articleWithComments(comments: List[Comment]): Article =
    Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      comments
    )

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
}
