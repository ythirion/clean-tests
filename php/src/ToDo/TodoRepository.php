<?php

namespace Com\CleanTests\ToDo;

interface TodoRepository
{
    function search(string $text): array;
}