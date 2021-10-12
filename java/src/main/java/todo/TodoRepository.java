package todo;

import io.vavr.collection.List;

public interface TodoRepository {
    List<Todo> search(String text);
}