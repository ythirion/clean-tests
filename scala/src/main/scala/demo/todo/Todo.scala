package demo.todo

final case class Todo(title: String, description: String)

trait TodoRepository {
  def search(text: String): List[Todo]
}

class ToDoService(
    private val repository: TodoRepository
) {
  def search(text: String): List[Todo] = {
    repository.search(text)
  }
}
