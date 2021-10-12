package antipatterns;

import blog.Article;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlogTests {
    @Test
    void it_should_return_a_Right_for_valid_comment() {
        val article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        val result = article.addComment("Amazing article !!!", "Pablo Escobar");

        assertThat(result.isRight()).isTrue();
    }

    @Test
    void it_should_add_a_comment_with_the_given_text() {
        val article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        val text = "Amazing article !!!";
        val result = article.addComment(text, "Pablo Escobar");

        assertThat(result.get().getComments())
                .hasSize(1)
                .anyMatch(comment -> comment.getText().equals(text));
    }

    @Test
    void it_should_add_a_comment_with_the_given_author() {
        val article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        val author = "Pablo Escobar";
        val result = article.addComment("Amazing article !!!", author);

        assertThat(result.get().getComments())
                .hasSize(1)
                .anyMatch(comment -> comment.getAuthor().equals(author));
    }

    @Test
    void it_should_add_a_comment_with_the_date_of_the_day() {
        val article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        val result = article.addComment("Amazing article !!!", "Pablo Escobar");
    }

    @Test
    void it_should_return_a_Left_when_adding_existing_comment() {
        val article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        val result =
                article.addComment("Amazing article !!!", "Pablo Escobar")
                        .map(a -> a.addComment("Amazing article !!!", "Pablo Escobar"))
                        .flatMap(r -> r);

        assertThat(result.isLeft()).isTrue();
    }
}
