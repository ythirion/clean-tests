<?php

namespace AntiPatterns;

use Com\CleanTests\Blog\Article;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertCount;
use function PHPUnit\Framework\assertEquals;

class BlogTests extends TestCase
{
    public function testShouldReturnNothingForAValidComment(): void
    {
        $article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        $result = $article->addComment("Amazing article !!!", "Pablo Escobar");

        assertCount(0, $result);
    }

    public function testShouldAddACommentWithTheGivenText(): void
    {
        $article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        $text = "Amazing article !!!";
        $article->addComment($text, "Pablo Escobar");

        assertCount(1, $article->getComments());
        assertEquals($text, $article->getComments()[0]->getText());
    }

    public function testShouldAddACommentWithTheGivenAuthor(): void
    {
        $article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        $author = "Pablo Escobar";
        $article->addComment("Amazing article !!!", $author);

        assertCount(1, $article->getComments());
        assertEquals($author, $article->getComments()[0]->getAuthor());
    }

    public function testShouldAddACommentWithTheDateOfTheDay(): void
    {
        $article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        $article->addComment("Amazing article !!!", "Pablo Escobar");
        assertCount(1, $article->getComments());
    }

    public function testShouldFailWhenAddingExistingComment(): void
    {
        $article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        $article->addComment("Amazing article !!!", "Pablo Escobar");
        $result = $article->addComment("Amazing article !!!", "Pablo Escobar");

        assertCount(1, $article->getComments());
        assertCount(1, $result);
        assertEquals("Comment already in the article", $result[0]->getDescription());
    }
}