package blog;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Comment {
    private final String text;
    private final String author;
    private final LocalDate creationDate;
}
