package antipatterns.solution;

import blog.Article;
import blog.Comment;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Blog_should {
    private final String text = "Amazing article !!!";
    private final String author = "Pablo Escobar";
    private Article article;

    private static void assertComment(Comment comment,
                                      String expectedText,
                                      String expectedAuthor) {
        assertThat(comment.getText()).isEqualTo(expectedText);
        assertThat(comment.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(comment.getCreationDate()).isBeforeOrEqualTo(LocalDate.now());
    }

    @BeforeEach
    void init() {
        article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    @Nested
    class add_a_new_comment {
        @Test
        void in_the_article_including_given_text_and_author() {
            val updatedArticle = article.addComment(text, author);

            assertThat(updatedArticle.isRight()).isTrue();
            assertComment(updatedArticle.get().getComments().head(), text, author);
        }

        @Test
        void in_an_article_containing_existing_ones() {
            val newText = "Finibus Bonorum et Malorum";
            val newAuthor = "Al Capone";

            val updatedArticle = article.addComment(text, author)
                    .map(a -> a.addComment(newText, newAuthor))
                    .flatMap(r -> r);

            assertThat(updatedArticle.isRight()).isTrue();
            assertThat(updatedArticle.get().getComments()).hasSize(2);
            assertComment(updatedArticle.get().getComments().last(), newText, newAuthor);
        }
    }

    @Nested
    class return_an_error {
        @Test
        void when_adding_an_existing_comment() {
            val updatedArticle = article.addComment(text, author)
                    .map(a -> a.addComment(text, author))
                    .flatMap(r -> r);

            assertThat(updatedArticle.isLeft()).isTrue();
            assertThat(updatedArticle.getLeft())
                    .hasSize(1)
                    .allMatch(error -> error.getDescription().equals("Comment already in the article"));
        }
    }
}
