<?php

namespace Com\CleanTests\Customer;

class Store
{
    private array $inventory;

    public function __construct()
    {
        $this->inventory = [];
    }

    public function addInventory(ProductType $productType, int $quantity): void
    {
        $this->updateStore($productType, $this->getInventoryFor($productType) + $quantity);
    }

    public function removeInventory(ProductType $productType, int $quantity): void
    {
        $this->updateStore($productType, $this->getInventoryFor($productType) - $quantity);
    }

    public function hasEnoughInventory(ProductType $productType, int $quantity): bool
    {
        return $this->getInventoryFor($productType, $quantity) >= $quantity;
    }

    public function getInventoryFor(ProductType $productType): int
    {
        return array_key_exists($productType->name, $this->inventory)
            ? $this->inventory[$productType->name]
            : 0;
    }

    private function updateStore(ProductType $productType, int $newQuantity): void
    {
        $this->inventory[$productType->name] = $newQuantity;
    }

    /*function hasEnoughInventory(ProductType $productType, int $quantity): void;



    function updateStore(ProductType $productType, int $newQuantity): int;*/
}