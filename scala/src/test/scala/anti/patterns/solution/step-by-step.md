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
class BlogTests extends AnyFlatSpec with EitherValues {
  // Have a business oriented name for the test
  // What is a Right ? What is a valid comment ?
  it should "returns a Right for valid comment" in {
    // Repeated in each test
    // Duplication everywhere
    val blogService = new BlogService()
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
    )
    val result = blogService.addComment(article, "Amazing article !!!", "Pablo Escobar")

    assert(result.isRight)
  }

  // 1 test to test each comment value...
  // 4 tests instead of 1 to maintain
  // Tests should be behavior focus -> not data driven
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
    // Missing assertions
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

    // What is inside the Left ?
    assert(result.isLeft)
  }
}
```

* Remove duplication
  * Extract `BlogService`, article instantiation and constants

```scala
class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"
  private val blogService = new BlogService()
  private val article = Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
  )

  it should "returns a Right for valid comment" in {
    val result = blogService.addComment(article, text, author)
    assert(result.isRight)
  }

  it should "add a comment with the given text" in {
    val result = blogService.addComment(article, text, author)
    assert(result.value.comments.head.text == text)
  }

  it should "add a comment with the given author" in {
    val result = blogService.addComment(article, text, author)
    assert(result.value.comments.head.author == author)
  }

  it should "add a comment with the date of the day" in {
    val result = blogService.addComment(article, text, author)
  }

  it should "returns a Left when adding existing comment" in {
    // We still have some duplications here
    val article = Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      List(Comment("Amazing article !!!", "Pablo Escobar", LocalDate.now()))
    )
    val result = blogService.addComment(article, text, author)

    assert(result.isLeft)
  }
}
```

* Isolate `Article` instantiation
  * Create helper functions to Arrange our `Article`
  * If you need to instantiate a lot of object, centralize their instantiation in [TestDataBuilders](https://www.arhohuttunen.com/test-data-builders/)
    * If you change your models, it will be easier to maintain
```scala
class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"
  private val blogService = new BlogService()
  private val emptyArticle: Article = articleWithComments(List.empty[Comment])
  private val articleWith1Comment: Article = articleWithComments(List(Comment(text, author, LocalDate.now())))

  private def articleWithComments(comments: List[Comment]): Article =
    Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      comments
    )

  it should "returns a Right for valid comment" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.isRight)
  }

  it should "add a comment with the given text" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.value.comments.head.text == text)
  }

  it should "add a comment with the given author" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.value.comments.head.author == author)
  }

  it should "add a comment with the date of the day" in {
    val author = "Pablo Escobar"
    val result = blogService.addComment(emptyArticle, text, author)
  }

  it should "returns a Left when adding existing comment" in {
    val result = blogService.addComment(articleWith1Comment, text, author)

    assert(result.isLeft)
  }
}
```

* Each test should represent a behavior
  * Not data oriented
  * Have a single test for the tested behavior
```scala
  it should "returns a Right for valid comment with the given text and given author" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.isRight)
    assert(result.value.comments.head.text == text)
    assert(result.value.comments.head.author == author)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }
```

* Be careful with date
  * Non deterministic data
  * Ideally pass a function to handle "time" (Clock)
    * Make it possible to mock time from tests
```scala
class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"
  private val blogService = new BlogService()
  private val emptyArticle: Article = articleWithComments(List.empty[Comment])
  private val articleWith1Comment: Article = articleWithComments(List(Comment(text, author, LocalDate.now())))

  private def articleWithComments(comments: List[Comment]): Article =
    Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      comments
    )

  it should "returns a Right for valid comment with the given text and given author" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.isRight)
    assert(result.value.comments.head.text == text)
    assert(result.value.comments.head.author == author)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }

  it should "returns a Left when adding existing comment" in {
    val result = blogService.addComment(articleWith1Comment, text, author)
    assert(result.isLeft)
  }
}
```

* Rename the test to remove implementation details / technical jargon
```scala
  it should "add a new comment in an empty Article including given text / author" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.isRight)
    assert(result.value.comments.head.text == text)
    assert(result.value.comments.head.author == author)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }
```

* Improve the error test case
  * Rename the test
  * Assert the validation error

```scala
  it should "return an error when adding existing comment" in {
    val updatedArticle = blogService.addComment(articleWith1Comment, text, author)

    assert(updatedArticle.isLeft)
    assert(updatedArticle.left.value.size == 1)
    assert(updatedArticle.left.value.head.description == "Comment already in the article")
  }
```

* Add missing test case
  * What happens if we add a comment on an Article containing existing ones ?

```scala
  it should "add a new comment in an Article containing existing comments" in {
    val newText = "Finibus Bonorum et Malorum"
    val newAuthor = "Al Capone"

    val result = blogService.addComment(articleWith1Comment, newText, newAuthor)

    assert(result.isRight)
    assert(result.value.comments.size == 2)
    assert(result.value.comments.head.text == newText)
    assert(result.value.comments.head.author == newAuthor)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }
```

* Could we improve our tests again ?
```scala
class BlogRefactoredTests extends AnyFlatSpec with EitherValues {
  private val text = "Amazing article !!!"
  private val author = "Pablo Escobar"
  private val blogService = new BlogService()
  private val emptyArticle: Article = articleWithComments(List.empty[Comment])
  private val articleWith1Comment: Article = articleWithComments(List(Comment(text, author, LocalDate.now())))

  private def articleWithComments(comments: List[Comment]): Article =
    Article(
      "Lorem Ipsum",
      "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
      comments
    )

  it should "returns a Right for valid comment with the given text and given author" in {
    val result = blogService.addComment(emptyArticle, text, author)
    assert(result.isRight)
    assert(result.value.comments.head.text == text)
    assert(result.value.comments.head.author == author)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }

  it should "add a new comment in an Article containing existing comments" in {
    val newText = "Finibus Bonorum et Malorum"
    val newAuthor = "Al Capone"

    val result = blogService.addComment(articleWith1Comment, newText, newAuthor)

    assert(result.isRight)
    assert(result.value.comments.size == 2)
    assert(result.value.comments.head.text == newText)
    assert(result.value.comments.head.author == newAuthor)
    assert(result.value.comments.head.creationDate.isEqual(LocalDate.now))
  }

  it should "return an error when adding existing comment" in {
    val updatedArticle = blogService.addComment(articleWith1Comment, text, author)

    assert(updatedArticle.isLeft)
    assert(updatedArticle.left.value.size == 1)
    assert(updatedArticle.left.value.head.description == "Comment already in the article")
  }
}
```
* Remove ambiguous names like `result`, `context`, ...
  * Rename `result` into `updatedArticle` (a more business oriented name)
* Centralize comment added assertions
```scala
   it should "returns a Right for valid comment with the given text and given author" in {
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
* Isolate Test glue in `companion` object
```scala
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
```