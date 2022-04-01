<?php

namespace Styles;

use Com\CleanTests\Styles\StateBased\Order;
use Com\CleanTests\Styles\StateBased\Product;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertCount;
use function PHPUnit\Framework\assertEquals;

class StateBasedTests extends TestCase
{
    public function testAddGivenProductToTheOrder(): void
    {
        $product = new Product("Free Guy");
        $sut = new Order();

        $sut->addProduct($product);

        // Verify the state
        assertCount(1, $sut->getProducts());
        assertEquals($product, $sut->getProducts()[0]);
    }
}