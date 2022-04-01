<?php

namespace Schools;

use Com\CleanTests\Customer\CustomerService;
use Com\CleanTests\Customer\ProductType;
use Com\CleanTests\Customer\Store;
use InvalidArgumentException;
use Mockery;
use PHPUnit\Framework\TestCase;

class LondonCustomerTests extends TestCase
{
    private Store $storeMock;
    private CustomerService $customerService;
    private int $quantity = 6;

    protected function setUp(): void
    {
        parent::setUp();
        $this->storeMock = Mockery::mock(Store::class);
        $this->customerService = new CustomerService();
    }

    /**
     * @doesNotPerformAssertions
     */
    public function testShouldPurchaseSuccessfullyWhenEnoughInventory(): void
    {
        $this->storeMock->shouldReceive('hasEnoughInventory')
            ->withArgs([ProductType::Book, $this->quantity])
            ->times(1)
            ->andReturn(true);

        $this->storeMock->shouldReceive('removeInventory')
            ->withArgs([ProductType::Book, $this->quantity]);

        $this->customerService->purchase($this->storeMock, ProductType::Book, $this->quantity);

        $this->storeMock->shouldHaveReceived('removeInventory')
            ->withArgs([ProductType::Book, $this->quantity])
            ->times(1);
    }

    public function testShouldFailWhenNotEnoughInventory(): void
    {
        $this->storeMock->shouldReceive('hasEnoughInventory')
            ->withArgs([ProductType::Book, $this->quantity])
            ->times(1)
            ->andReturn(false);

        $this->storeMock->shouldNotHaveReceived('removeInventory');

        $this->expectException(InvalidArgumentException::class);
        $this->customerService->purchase($this->storeMock, ProductType::Book, $this->quantity);
    }
}