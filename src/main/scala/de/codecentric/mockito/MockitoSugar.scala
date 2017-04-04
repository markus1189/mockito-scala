package de.codecentric.mockito

import org.mockito.stubbing.{Answer, OngoingStubbing}
import org.mockito._
import org.mockito.invocation.InvocationOnMock

import scala.concurrent.Future
import scala.reflect.ClassTag

object MockitoSugar extends MockitoSugar

trait MockitoSugar
    extends MockingSyntax
    with ArgumentMatchingSyntax
    with StubbingSyntax
    with VerificationSyntax
    with MockSyntax

trait MockingSyntax {
  def mockWith[A <: AnyRef: ClassTag](settings: MockSettings): Mocked[A] = {
    Mockito.mock[A](runtimeClass, settings).asInstanceOf[Mocked[A]]
  }

  def mock[A <: AnyRef: ClassTag]: Mocked[A] =
    mockWith[A](Mockito.withSettings)

  def mockNamed[A <: AnyRef: ClassTag](name: String): Mocked[A] =
    mockWith[A](Mockito.withSettings.name(name))

  def mockSmart[A <: AnyRef: ClassTag]: Mocked[A] =
    mockWith[A](
      Mockito.withSettings.defaultAnswer(Mockito.RETURNS_SMART_NULLS))

  def mockDeep[A <: AnyRef: ClassTag]: Mocked[A] =
    mockWith[A](Mockito.withSettings.defaultAnswer(Mockito.RETURNS_DEEP_STUBS))
}

trait StubbingSyntax {
  implicit class StubbingOps[A](mockee: => A) {
    def returns(r: A): OngoingStubbing[A] =
      Mockito.when(mockee).thenReturn(r)

    def returns(args: Seq[A]) = args match {
      case Seq() =>
        throw new IllegalArgumentException(
          "Empty list of return values is not allowed.")
      case r +: rs =>
        rs.foldLeft(Mockito.when(mockee).thenReturn(r)) { (stubbing, r1) =>
          stubbing.thenReturn(r1)
        }
    }

    def returnsDefault(implicit default: MockDefault[A]): OngoingStubbing[A] =
      Mockito.when(mockee).thenReturn(default.value)

    def throws[E <: Throwable](e: E): OngoingStubbing[A] = {
      Mockito.when(mockee).thenThrow(e)
    }

    def callsRealMethod: OngoingStubbing[A] =
      Mockito.when(mockee).thenCallRealMethod()

    def answers(f: InvocationOnMock => A) =
      Mockito
        .when(mockee)
        .thenAnswer(new Answer[A] {
          override def answer(invocation: InvocationOnMock): A = f(invocation)
        })
  }
}

trait MockSyntax {
  implicit class MockedOps[A](mock: Mocked[A]) {
    def details: MockingDetails = Mockito.mockingDetails(mock)
    def reset(): Unit = Mockito.reset(mock)
  }
}

trait VerificationSyntax {
  def there: There = new There

  class There {
    def are: Are = new Are
    def was: Was = new Was
    def were: Were = new Were
  }

  class Are {
    def noMoreInteractions[A](mock: Mocked[A]): Unit =
      Mockito.verifyNoMoreInteractions(mock)
  }

  class Was {
    def one[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.times(1))
    def atLeastOne[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.atLeast(1))
    def atMostOne[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.atMost(1))
    def only[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.only())
  }

  class Were {
    def two[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.times(2))
    def exactly[A](n: Int)(mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.times(n))
    def no[A](mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.never())
    def atLeast[A](n: Int)(mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.atLeast(n))
    def atMost[A](n: Int)(mock: Mocked[A]): Mocked[A] =
      Mockito.verify(mock, Mockito.atMost(n))
    def zeroInteractions[A](mock: Mocked[A]): Unit =
      Mockito.verifyZeroInteractions(mock)
  }
}

trait ArgumentMatchingSyntax {
  def any[A: ClassTag]: A = ArgumentMatchers.any[A]()
  def anyNonNull[A: ClassTag]: A = ArgumentMatchers.any[A](runtimeClass[A])
  def exactly[A](value: A): A = ArgumentMatchers.eq[A](value)

  def argThat[A](p: A => Boolean) =
    ArgumentMatchers.argThat[A](new ArgumentMatcher[A] {
      override def matches(argument: A): Boolean = p(argument)
    })
}

trait MockDefault[+A] {
  def value: A
}

object MockDefault {
  def fromValue[A](x: A): MockDefault[A] = new MockDefault[A] {
    override def value: A = x
  }

  implicit val defaultInt: MockDefault[Int] = fromValue(0)
  implicit val defaultString: MockDefault[String] = fromValue("")

  implicit def defaultOption[A]: MockDefault[Option[A]] = fromValue(None)

  implicit def defaultFuture[A](
      implicit D: MockDefault[A]): MockDefault[Future[A]] =
    fromValue(Future.successful(D.value))
}
