package demo.styles

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class CommunicationBasedTests extends AnyFlatSpec with MockFactory {
  it should "send greetings email" in {
    val emailGatewayStub = stub[EmailGateway]
    // Substitute collaborators with Test Double
    val sut = new Controller(emailGatewayStub)

    sut.greetUser("john.doe@email.com")

    // Verify that the SUT calls those collaborators correctly
    (emailGatewayStub.sendGreetingsEmail _).verify("john.doe@email.com").once()
  }

  trait EmailGateway {
    def sendGreetingsEmail(email: String): Try[String]
  }

  class Controller(private val emailGateway: EmailGateway) {
    def greetUser(email: String): Try[String] =
      emailGateway.sendGreetingsEmail(email)
  }
}
