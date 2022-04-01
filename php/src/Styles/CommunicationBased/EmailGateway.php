<?php

namespace Com\CleanTests\Styles\CommunicationBased;

interface EmailGateway
{
    function sendGreetingsEmail(string $email): void;
}