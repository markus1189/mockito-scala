package org.markushauck.mockito

import org.scalatest._
import org.scalactic._

class MockitoSugarSpec extends WordSpec with Matchers with TypeCheckedTripleEquals {
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
      val m    = mock[Foo]

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

    "allow simple stubbing via 'answers'" in {
      val m = mock[Foo]

      var n = 0.0

      m.baz answers { _ =>
        n += 1
        n
      }

      m.baz should ===(1.0)
      m.baz should ===(2.0)
    }

    "not allow mocking of values" in {
      assertTypeError("mock[Int]")
      assertTypeError("mock[Char]")
      assertTypeError("mock[Boolean]")
    }

    "not allow verification on non-mocks" in {
      assertTypeError("""
val realFoo = new Foo
there.was.one(realFoo).bar
""")
    }

    "support verification sugar with 'one'" in {
      val m = mock[Foo]

      m.bar

      m.baz
      m.baz

      there.was.one(m).bar
      there.were.two(m).baz
      there.were.no(m).qux
    }

    "support arg forwarding" in {
      val m = mock[Foo]

      m.quz(any[Int]) forwardsArg 0

      m.quz(42) should ===(42)
    }
  }
}

class Foo {
  def bar: String      = "foobar"
  def baz: Double      = 42.0
  def qux: Object      = new {}
  def quz(i: Int): Int = i + 1
}
