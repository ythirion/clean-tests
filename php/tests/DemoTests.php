<?php

use Com\CleanTests\Demo;
use PHPUnit\Framework\TestCase;

final class DemoTests extends TestCase
{
    public function testReturnFalseForABC(): void
    {
        $demo = new Demo();
        $this->assertFalse($demo->isLong("abc"));
    }
}