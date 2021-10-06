package anti.patterns

import demo.todo.{ToDoService, Todo, TodoRepository}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

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
