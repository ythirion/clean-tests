package styles;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommunicationBasedTests {
    @Test
    void it_should_send_greeting_email() {
        final var email = "john.doe@email.com";
        final var emailGatewayMock = mock(EmailGateway.class);
        // Substitute collaborators with Test Double
        final var sut = new Controller(emailGatewayMock);

        sut.greetUser(email);

        // Verify that the SUT calls those collaborators correctly
        verify(emailGatewayMock, times(1)).sendGreetingsEmail(email);
    }

    interface EmailGateway {
        Try<String> sendGreetingsEmail(String email);
    }

    @AllArgsConstructor
    class Controller {
        private final EmailGateway emailGateway;

        public Try<String> greetUser(String email) {
            return emailGateway.sendGreetingsEmail(email);
        }
    }
}

