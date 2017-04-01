package de.codecentric

import scala.reflect.ClassTag

package object mockito {
  private[mockito] def runtimeClass[A](implicit CT: ClassTag[A]): Class[A] =
    CT.runtimeClass.asInstanceOf[Class[A]]

  type Mock[A] = { type From = A }
  type Mocked[A] = A with Mock[A]
}
