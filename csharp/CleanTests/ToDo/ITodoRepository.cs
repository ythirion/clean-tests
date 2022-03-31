using LanguageExt;

namespace CleanTests.ToDo;

public interface ITodoRepository
{
    Seq<Todo> Search(string text);
}