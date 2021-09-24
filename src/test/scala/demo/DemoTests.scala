package demo

import org.scalatest.flatspec.AnyFlatSpec

class DemoTests extends AnyFlatSpec {
  it should "return false for abc String" in {
    Demo.isLong("abc")
  }
}