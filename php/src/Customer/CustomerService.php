<?php

namespace Com\CleanTests\Customer;

use InvalidArgumentException;

class CustomerService
{
    function purchase(Store       $store,
                      ProductType $productType,
                      int         $quantity): void
    {
        if (!$store->hasEnoughInventory($productType, $quantity))
            throw new InvalidArgumentException("Not enough inventory");
        else $store->removeInventory($productType, $quantity);
    }
}