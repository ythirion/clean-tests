<?php

namespace Com\CleanTests\Blog;

class Article
{
    private const DATE_FORMAT = "Ymd";
    private string $name;
    private string $content;
    private array $comments;

    public function __construct(string $name, string $content, array $comments = [])
    {
        $this->name = $name;
        $this->content = $content;
        $this->comments = $comments;
    }

    public function addComment(string $text, string $author): array
    {
        $comment = new Comment($text, $author, date(self::DATE_FORMAT));
        $result = [];

        if (in_array($comment, $this->comments))
            $result[] = new ValidationError("Comment already in the article");
        else $this->comments[] = $comment;

        return $result;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getContent(): string
    {
        return $this->content;
    }

    public function getComments(): array
    {
        return $this->comments;
    }
}