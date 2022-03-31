using LanguageExt;

namespace CleanTests.ToDo;

public class TodoService
{
    private readonly ITodoRepository _todoRepository;

    public TodoService(ITodoRepository todoRepository)
    {
        _todoRepository = todoRepository;
    }

    public Seq<Todo> Search(string text)
        => _todoRepository.Search(text);
}