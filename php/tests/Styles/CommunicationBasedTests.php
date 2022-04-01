<?php

namespace Styles;

use Com\CleanTests\Styles\Controller;
use Com\CleanTests\Styles\EmailGateway;
use Mockery;
use PHPUnit\Framework\TestCase;

class CommunicationBasedTests extends TestCase
{
    /**
     * @doesNotPerformAssertions
     */
    public function testGreetAUserShouldSendAnEmail(): void
    {
        $email = "john.doe@email.com";
        $emailGatewayMock = Mockery::mock(EmailGateway::class);

        $emailGatewayMock->shouldReceive('sendGreetingsEmail')
            ->withArgs([$email]);

        // Substitute collaborators with Test Double
        $sut = new Controller($emailGatewayMock);
        $sut->greetUser($email);

        // Verify that the SUT calls collaborators correctly
        $emailGatewayMock->shouldHaveReceived('sendGreetingsEmail')
            ->withArgs([$email])
            ->times(1);
    }
}