package antipatterns;

import io.vavr.collection.List;
import lombok.val;
import org.junit.jupiter.api.Test;
import todo.Todo;
import todo.TodoRepository;
import todo.TodoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodoTests {
    @Test
    void it_should_call_search_on_repository_with_the_given_text() {
        val todoRepositoryMock = mock(TodoRepository.class);
        val todoService = new TodoService(todoRepositoryMock);

        val searchResults = List.of(
                new Todo("Create miro", "add code samples in the board"),
                new Todo("Add myths in miro", "add mythbusters from ppt in the board")
        );
        val searchedText = "miro";

        when(todoRepositoryMock.search(searchedText))
                .thenReturn(searchResults);

        val result = todoRepositoryMock.search(searchedText);

        assertThat(result).isEqualTo(searchResults);
        verify(todoRepositoryMock, times(1)).search(searchedText);
    }
}
