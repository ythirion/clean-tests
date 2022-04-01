<?php

namespace AntiPatterns;

use Com\CleanTests\ToDo\Todo;
use Com\CleanTests\ToDo\TodoService;
use Mockery;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertEquals;

class TodoTests extends TestCase
{
    public function testItShouldSearchOnRepositoryWithTheGivenText(): void
    {
        $todoRepositoryMock = Mockery::mock('TodoRepository');
        $todoService = new TodoService($todoRepositoryMock);

        $searchResults = [
            new Todo("Create miro", "add code samples in the board"),
            new Todo("Add myths in miro", "add mythbusters from ppt in the board")
        ];

        $searchedText = "miro";

        $todoRepositoryMock->shouldReceive('search')
            ->withArgs([$searchedText])
            ->times(1)
            ->andReturn($searchResults);

        $results = $todoRepositoryMock->search($searchedText);

        assertEquals($searchResults, $results);
    }
}