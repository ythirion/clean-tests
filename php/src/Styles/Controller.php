<?php

namespace Com\CleanTests\Styles;

final class Controller
{
    private EmailGateway $emailGateway;

    public function __construct(EmailGateway $emailGateway)
    {
        $this->emailGateway = $emailGateway;
    }

    public function greetUser(string $userEmail): void
    {
        $this->emailGateway->sendGreetingsEmail($userEmail);
    }
}