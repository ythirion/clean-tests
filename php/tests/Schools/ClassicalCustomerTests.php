<?php

namespace Schools;

use Com\CleanTests\Customer\CustomerService;
use Com\CleanTests\Customer\ProductType;
use Com\CleanTests\Customer\Store;
use InvalidArgumentException;
use PHPUnit\Framework\TestCase;
use function PHPUnit\Framework\assertEquals;

class ClassicalCustomerTests extends TestCase
{
    private Store $store;
    private CustomerService $customerService;

    protected function setUp(): void
    {
        parent::setUp();
        $this->store = new Store();
        $this->customerService = new CustomerService($this->store);
    }

    public function testShouldPurchaseSuccessfullyWhenEnoughInventory(): void
    {
        $this->store->addInventory(ProductType::Book, 10);
        $this->customerService->purchase($this->store, ProductType::Book, 6);

        assertEquals(4, $this->store->getInventoryFor(ProductType::Book));
    }

    public function testShouldFailWhenNotEnoughInventory(): void
    {
        $this->store->addInventory(ProductType::Book, 10);

        $this->expectException(InvalidArgumentException::class);
        $this->customerService->purchase($this->store, ProductType::Book, 11);
    }
}