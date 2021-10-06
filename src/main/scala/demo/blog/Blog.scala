package demo.blog

import java.time.LocalDate

case class Comment(text: String, author: String, creationDate: LocalDate)

case class ValidationError(description: String)

final class Article(
    val name: String,
    val content: String,
    val comments: List[Comment] = List.empty[Comment]
) {
  def addComment(
      text: String,
      author: String
  ): Either[List[ValidationError], Article] = {
    addComment(text, author, LocalDate.now)
  }

  private def addComment(
      text: String,
      author: String,
      creationDate: LocalDate
  ): Either[List[ValidationError], Article] = {
    val comment = Comment(text, author, creationDate)

    if (comments.contains(comment))
      Left(List(ValidationError("Comment already in the article")))
    else Right(new Article(name, content, comment :: comments))
  }
}
