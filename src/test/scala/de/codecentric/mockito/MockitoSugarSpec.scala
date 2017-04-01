package de.codecentric.mockito

import org.scalatest._
import org.scalactic._

class MockitoSugarSpec
    extends WordSpec
    with Matchers
    with TypeCheckedTripleEquals {
  import MockitoSugar._

  "MockitoSugar" should {
    "allow simple stubbing via 'returns'" in {
      val m = mock[Foo]

      m.bar returns "42"

      m.bar should ===("42")
    }

    "allow chained stubbing" in {
      val m = mock[Foo]

      m.bar returns "42" thenReturn "43" thenThrow new IllegalArgumentException

      m.bar should ===("42")
      m.bar should ===("43")
      an[IllegalArgumentException] should be thrownBy m.bar
    }

    "allow chained stubbing via multiple arguments" in {
      val args = Seq.tabulate(5)(_.toString)
      val m = mock[Foo]

      m.bar returns args

      args.foreach { arg =>
        m.bar should ===(arg)
      }
    }

    "allow simple stubbing via 'throws'" in {
      val m = mock[Foo]

      m.bar throws new RuntimeException

      an[RuntimeException] should be thrownBy m.bar
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
