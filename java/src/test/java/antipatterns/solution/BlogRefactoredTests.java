package antipatterns.solution;

import blog.Article;
import blog.Comment;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class BlogRefactoredTests {
    private final String text = "Amazing article !!!";
    private final String author = "Pablo Escobar";
    private Article article;

    @BeforeEach
    void init() {
        article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    @Test
    void it_should_add_a_new_comment_in_the_article_including_given_text_and_author() {
        val result = article.addComment(text, author);

        assertThat(result.isRight()).isTrue();
        assertComment(result.get().getComments().head(), text, author);
    }

    private void assertComment(Comment comment,
                               String expectedText,
                               String expectedAuthor) {
        assertThat(comment.getText()).isEqualTo(expectedText);
        assertThat(comment.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(comment.getCreationDate()).isCloseTo(LocalDate.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void it_should_return_an_error_when_adding_an_existing_comment() {
        val result =
                article.addComment(text, author)
                        .map(a -> a.addComment(text, author))
                        .flatMap(r -> r);

        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft())
                .hasSize(1)
                .allMatch(error -> error.getDescription().equals("Comment already in the article"));
    }
}
