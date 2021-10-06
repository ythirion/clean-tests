package anti.patterns

import demo.todo.{ToDoService, Todo, TodoRepository}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

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

    val result = todoRepositoryStub.search(searchedText)

    assert(result == searchResults)
    (todoRepositoryStub.search _).verify(searchedText).once()
  }
}
