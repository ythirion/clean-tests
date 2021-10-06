## PriceEngineTests
```scala
class PriceEngineTests extends AnyFlatSpec {
  it should "calculate the discount of 2 products" in {
    val products = List(Product("P1"), Product("P2"), Product("P3"))

    val discount = PriceEngine.calculateDiscount(products: _*)

    // Production code leaked / duplicated in tests
    assert(products.length * 0.01 == discount)
  }
}
```

* Test is always a sample
  * Refactor this test
```scala
class PriceEngineRefactoredTests extends AnyFlatSpec {
  it should "calculate the discount of 2 products" in {
    val products = List(Product("P1"), Product("P2"), Product("P3"))

    val discount = PriceEngine.calculateDiscount(products: _*)

    // Fine to use "hardcoded" values in test
    // It is from our test case
    assert(0.03 == discount)
  }
}
```

## TodoTests
```scala
class TodoTests extends AnyFlatSpec with MockFactory {
  it should "call search on repository when searching given text" in {
    val todoRepositoryStub = stub[TodoRepository]
    val toDoService = new ToDoService(todoRepositoryStub)
    val searchResults = List(
      Todo("Create mural", "add code samples in mural"),
      Todo("Add myths in mural", "add mythbusters from ppt in the board")
    )
    val searchedText = "mural"

    (todoRepositoryStub.search _)
      .when(searchedText)
      .returns(searchResults)

    // The Subject Under Test is a mock
    val result = todoRepositoryStub.search(searchedText)

    // Here we assert that the call on our mock returns what we setup
    // We test scalamock...
    assert(result == searchResults)
    (todoRepositoryStub.search _).verify(searchedText).once()
  }
}
```

* Use the true SUT
```scala
class TodoRefactoredTests extends AnyFlatSpec with MockFactory {
  it should "call search on repository when searching given text" in {
    val todoRepositoryStub = stub[TodoRepository]
    val toDoService = new ToDoService(todoRepositoryStub)
    val searchResults = List(
      Todo("Create mural", "add code samples in mural"),
      Todo("Add myths in mural", "add mythbusters from ppt in the board")
    )
    val searchedText = "mural"

    (todoRepositoryStub.search _)
      .when(searchedText)
      .returns(searchResults)

    // The SUT is the TodoService that (in this simple version) will only delegates call to Repository
    // Would have more responsibility in real life (authorization, quotas, filtering, ...)
    val result = toDoService.search(searchedText)

    (todoRepositoryStub.search _).verify(searchedText).once()
    assert(result == searchResults)
  }
}
```

## BlogTests
```scala
package anti.patterns

import demo.blog.Article
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

class BlogTests extends AnyFlatSpec with EitherValues {
	// Have a business oriented name for the test
  it should "return a Right for valid comment" in {
    // Repeated in each test
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = article.addComment("Amazing article !!!", "Pablo Escobar")

    assert(result.isRight)
  }

  // 1 test to test each comment value...
  // 4 tests instead of 1 to maintain
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

    // Missing assertions
  }

  it should "return a Left when adding existing comment" in {
    val article = new Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = article
      .addComment("Amazing article !!!", "Pablo Escobar")
      .map(_.addComment("Amazing article !!!", "Pablo Escobar"))
      .flatten
    
		// What is inside the Left ?
    assert(result.isLeft)
  }
}

```

* Remove duplication
  * If you need to instantiate a lot of object, centralize it in TestDataBuilders
    * If you change your models, it will be easier to maintain

```scala
package anti.patterns

import demo.blog.Article
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec

class BlogTestsRefactored extends AnyFlatSpec with EitherValues {
  private val article = new Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
  )

  it should "returns a Right for valid comment" in {
    val result = article.addComment("Amazing article !!!", "Pablo Escobar")

    assert(result.isRight)
  }

  it should "add a comment with the given text" in {
    val text = "Amazing article !!!"
    val result = article.addComment(text, "Pablo Escobar")

    assert(result.value.comments.head.text == text)
  }

  it should "add a comment with the given author" in {
    val author = "Pablo Escobar"
    val result = article.addComment("Amazing article !!!", author)

    assert(result.value.comments.head.author == author)
  }

  it should "add a comment with the date of the day" in {
    val author = "Pablo Escobar"
    val result = article.addComment("Amazing article !!!", author)
  }

  it should "returns a Left when adding existing comment" in {
    val result = article
      .addComment("Amazing article !!!", "Pablo Escobar")
      .map(_.addComment("Amazing article !!!", "Pablo Escobar"))
      .flatten

    assert(result.isLeft)
  }
}
```

* Each test should represent a behavior
  * Not data oriented

```scala
it should "returns a Right[Article] containing a new comment including given text / author" in {
  val text = "Amazing article !!!"
  val author = "Pablo Escobar"

  val result = article.addComment(text, author)

  assert(result.isRight)
  val addedComment = result.value.comments.head
  assert(addedComment.text == text)
  assert(addedComment.author == author)
  assert(addedComment.creationDate.isEqual(LocalDate.now))
}
```

* Be careful with date
  * Non deterministic data
  * Ideally pass handle it with a function

```scala
class BlogTestsRefactored extends AnyFlatSpec with EitherValues {
  private val article = new Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
  )

  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"

  // Have more business oriented
  it should "return a Right[Article] containing a new comment including given text / author" in {
    val result = article.addComment(text, author)

    assert(result.isRight)
    val addedComment = result.value.comments.head
    assert(addedComment.text == text)
    assert(addedComment.author == author)
    assert(addedComment.creationDate.isEqual(LocalDate.now))
  }

  it should "return a Left when adding existing comment" in {
    val result = article
      .addComment(text, author)
      .map(_.addComment(text, author))
      .flatten

    assert(result.isLeft)
  }
}
```

* Rename the test to remove implementation details

```scala
it should "add a new comment in the Article including given text / author" in {
  val text = "Amazing article !!!"
  val author = "Pablo Escobar"

  val result = article.addComment(text, author)

  assert(result.isRight)
  val addedComment = result.value.comments.head
  assert(addedComment.text == text)
  assert(addedComment.author == author)
  assert(addedComment.creationDate.isEqual(LocalDate.now))
}
```

* Improve the error test case
  * Rename the test
  * Assert the validation error

```scala
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
```

* Add missing test case
  * What happens if we add a comment on an Article containing existing ones

```scala
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
```

* Could we improve our tests again ?

```scala
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
```

* Centralize comment added assertions
  * Rename result into a more business oriented name

```scala
  it should "add a new comment in the Article including given text / author" in {
    val updatedArticle = article.addComment(text, author)
    assert(updatedArticle.isRight)
    assertAddedComment(updatedArticle.value, text, author)
  }

  it should "add a new comment in an Article containing existingh comments" in {
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
```

