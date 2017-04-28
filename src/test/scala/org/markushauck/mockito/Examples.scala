package org.markushauck.mockito

import org.mockito.invocation.InvocationOnMock

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Try

object Examples extends App with MockitoSugar {
  class Foo {
    def bar: String = "foobar"
    def baz(x: Int) = s"got: $x"
    def qux: Boolean = true
    def foobar: Future[Option[String]] = Future.successful(Some("some string"))
    def id[A](x: A): A = x
  }

  { // Standard mocking
    val m = mock[Foo]

    m.bar returns "nevermind"
    m.bar // => "nevermind"
  }

  { // Specifying multiple returns
    val m = mock[Foo]

    m.bar returnsMulti Seq("1", "2", "3")
    m.bar // => "1"
    m.bar // => "2"
    m.bar // => "3"
    m.bar // => "3" (BEWARE: subsequent invocations return the last element)
  }

  { // Using default returns
    val m = mock[Foo]

    m.foobar.returnsDefault

    Await.result(m.foobar, 1.second) // Future(None)
  }

  Try { // Throw exceptions
    val m = mock[Foo]

    m.baz(any[Int]) throws new IllegalArgumentException
    m.baz(1) // IllegalArgumentException
  }

  { // Answers, i.e., react to arguments
    val m = mock[Foo]

    m.baz(any[Int]) answers { (inv: InvocationOnMock) =>
      s"answers: ${inv.getArgument[Int](0) * 2}"
    }

    m.baz(5) // "answers: 10"
  }

  { // Forward arguments
    val m = mock[Foo]

    m.id(any[Int]).forwardsArg(0)

    m.id(5) // 5
    m.id(42) // 42
  }

  { // Verification
    val m = mock[Foo]

    m.bar
    m.bar
    m.baz(42)
    (1 to 5).foreach(_ => m.qux)

    there.was.one(m).baz(any[Int])
    there.were.two(m).bar
    there.were.atLeast(3)(m).qux
    there.were.exactly(5)(m).qux
    there.were.atMost(6)(m).qux
  }

}
