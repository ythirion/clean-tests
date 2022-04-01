<?php

namespace Com\CleanTests\Blog;

class Comment
{
    private string $text;
    private string $author;
    private string $creationDate;

    public function __construct(string $text, string $author, string $creationDate)
    {
        $this->text = $text;
        $this->author = $author;
        $this->creationDate = $creationDate;
    }

    public function getText(): string
    {
        return $this->text;
    }

    public function getAuthor(): string
    {
        return $this->author;
    }

    public function getCreationDate(): string
    {
        return $this->creationDate;
    }
}