<?php

namespace AntiPatterns;

use Com\CleanTests\Customer\PriceEngine;
use Com\CleanTests\Customer\Product;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertEquals;

class PriceEngineTests extends TestCase
{
    public function testDiscountOf3ProductsShouldBe3Percent(): void
    {
        $products = [new Product("P1"), new Product("P2"), new Product("P3")];
        $priceEngine = new PriceEngine();
        $discount = $priceEngine->calculateDiscount($products);

        assertEquals(count($products) * 0.01, $discount);
    }
}