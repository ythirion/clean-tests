package demo.styles

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class CommunicationBasedTests extends AnyFlatSpec with MockFactory {
  it should "send greetings email" in {
    val emailGatewayMock = stub[EmailGateway]
    val sut = new Controller(emailGatewayMock)

    sut.greetUser("john.doe@email.com")

    (emailGatewayMock.sendGreetingsEmail _).verify("john.doe@email.com").once()
  }

  trait EmailGateway {
    def sendGreetingsEmail(email: String): Try[String]
  }

  class Controller(private val emailGateway: EmailGateway) {
    def greetUser(email: String): Try[String] =
      emailGateway.sendGreetingsEmail(email)
  }
}
