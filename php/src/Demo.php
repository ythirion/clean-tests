<?php

namespace Com\CleanTests;

class Demo
{
    public function isLong(string $input): bool
    {
        return strlen($input) > 5;
    }
}