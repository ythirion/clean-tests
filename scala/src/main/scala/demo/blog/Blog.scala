package demo.blog

import java.time.LocalDate

final case class Comment(text: String, author: String, creationDate: LocalDate)

final case class ValidationError(description: String)

final case class Article(
    name: String,
    content: String,
    comments: List[Comment] = List.empty[Comment]
)

class BlogService {
  def addComment(
      article: Article,
      text: String,
      author: String
  ): Either[List[ValidationError], Article] = {
    addComment(article, text, author, LocalDate.now)
  }

  private def addComment(
      article: Article,
      text: String,
      author: String,
      creationDate: LocalDate
  ): Either[List[ValidationError], Article] = {
    val comment = Comment(text, author, creationDate)

    if (article.comments.contains(comment))
      Left(List(ValidationError("Comment already in the article")))
    else
      Right(Article(article.name, article.content, comment :: article.comments))
  }
}
