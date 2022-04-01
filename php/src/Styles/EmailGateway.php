<?php

namespace Com\CleanTests\Styles;

interface EmailGateway
{
    function sendGreetingsEmail(string $email): void;
}