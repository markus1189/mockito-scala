package de.codecentric.mockito

import org.scalatest._
import org.scalactic._

class MockitoSugarSpec extends WordSpec with Matchers with TypeCheckedTripleEquals {
  import MockitoSugar._

  "MockitoSugar" should {
    "allow simple stubbing via returns" in {
      val m = mock[Foo]

      m.bar returns "42"

      m.bar should ===("42")
    }

    "not allow mocking of values" in {
      assertTypeError("mock[Int]")
      assertTypeError("mock[Char]")
      assertTypeError("mock[Boolean]")
    }
  }
}

class Foo {
  def bar: String = "foobar"
}
