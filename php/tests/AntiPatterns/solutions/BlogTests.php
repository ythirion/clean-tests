<?php

namespace AntiPatterns\solutions\solutions;

use Com\CleanTests\Blog\Article;
use Com\CleanTests\Blog\Comment;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertCount;
use function PHPUnit\Framework\assertEquals;

class BlogTests extends TestCase
{
    private string $TEXT = "Amazing article !!!";
    private string $AUTHOR = "Pablo Escobar";
    private Article $article;

    protected function setUp(): void
    {
        parent::setUp();
        $this->article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    public function testAddANewCommentInTheArticleIncludingGivenTextAndAuthor(): void
    {
        $result = $this->article->addComment($this->TEXT, $this->AUTHOR);

        assertCount(0, $result);
        assertCount(1, $this->article->getComments());
        $this->assertComment($this->article->getComments()[0], $this->TEXT, $this->AUTHOR);
    }

    public function testAddANewCommentInTheArticleContainingExistingOnes(): void
    {
        $newText = "Finibus Bonorum et Malorum";
        $newAuthor = "Al Capone";

        $this->article->addComment($this->TEXT, $this->AUTHOR);
        $result = $this->article->addComment($newText, $newAuthor);

        assertCount(0, $result);
        assertCount(2, $this->article->getComments());
        $this->assertComment($this->article->getComments()[1], $newText, $newAuthor);
    }

    private function assertComment(Comment $comment,
                                   string  $expectedText,
                                   string  $expectedAuthor): void
    {
        assertEquals($expectedText, $comment->getText());
        assertEquals($expectedAuthor, $comment->getAuthor());
        assertEquals(date("Ymd"), $comment->getCreationDate());
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