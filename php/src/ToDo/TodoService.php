<?php

namespace Com\CleanTests\ToDo;

class TodoService
{
    private object $todoRepository;

    public function __construct($todoRepository)
    {
        $this->todoRepository = $todoRepository;
    }

    public function search(string $text): array
    {
        return $this->todoRepository->search($text);
    }
}