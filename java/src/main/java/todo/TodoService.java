package todo;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    public List<Todo> search(@NonNull String text) {
        return repository.search(text);
    }
}