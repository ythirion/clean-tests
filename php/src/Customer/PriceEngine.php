<?php

namespace Com\CleanTests\Customer;

class PriceEngine
{
    public function calculateDiscount(array $products): float
    {
        $discount = count($products) * 0.01;
        return min($discount, 0.2);
    }
}