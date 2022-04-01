<?php

namespace Styles;

use Com\CleanTests\Customer\PriceEngine;
use Com\CleanTests\Customer\Product;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertEquals;

class OutputBasedTests extends TestCase
{
    public function testDiscountOf2ProductsShouldBe2Percent(): void
    {
        $product1 = new Product("Kaamelott");
        $product2 = new Product("Free Guy");

        // Call on the SUT (here PriceEngine)
        // No side effects -> Pure function
        $priceEngine = new PriceEngine();
        $discount = $priceEngine->calculateDiscount([$product1, $product2]);

        assertEquals(0.02, $discount);
    }
}