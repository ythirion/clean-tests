package blog;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

import static io.vavr.API.Left;
import static io.vavr.API.Right;

@AllArgsConstructor
@Getter
public class Article {
    private final String name;
    private final String content;
    private final List<Comment> comments;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.comments = List.empty();
    }

    private Either<List<ValidationError>, Article> addComment(
            String text,
            String author,
            LocalDate creationDate) {
        val comment = new Comment(text, author, creationDate);

        return (comments.contains(comment)) ?
                Left(List.of(new ValidationError("Comment already in the article"))) :
                Right(new Article(name, content, comments.append(comment)));
    }

    public Either<List<ValidationError>, Article> addComment(String text, String author) {
        return addComment(text, author, LocalDate.now());
    }
}