<?php

namespace Com\CleanTests\Styles\StateBased;

class Order
{
    private array $products;

    public function addProduct(Product $product): void
    {
        $this->products[] = $product;
    }

    public function getProducts(): array
    {
        return $this->products;
    }
}