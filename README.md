## Mockito Sugar for Scala

[![Build Status](https://travis-ci.org/markus1189/mockito-scala.svg?branch=master)](https://travis-ci.org/markus1189/mockito-scala)
[![Maven Central](https://img.shields.io/maven-central/v/org.markushauck/mockitoscala_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/org.markushauck/mockitoscala_2.12)


### Overview

Inspired by [specs2](https://etorreborre.github.io/specs2/), this is a
Scala library that builds on [mockito](http://mockito.org) and
provides a thin wrapper around the API to make it more pleasurable
(and safer) to use in Scala.

*This library does not depend on any specific testing framework!*
Therefore you are free to use it with `specs2`, `ScalaTest` or
something else.

### Why?

The Java API for Mockito is good, but once you are used to some
syntactic conveniences from `specs2`'s `Mockito` trait, it feels very
clunky.  When using other test frameworks that `specs2`, there was up
until now nothing that provides this form of convenience, therefore
this library was written.

This library provides a lightweight Scala DSL around Mockito's Java
API.  The goals are:

  - more convenient syntax
  - less runtime exceptions on API misuse
  - easy to fall back to Java API when necessary

### How?

Add it to your `sbt` build:

```
libraryDependencies ++= Seq(
  "org.markushauck" %% "mockitoscala" % "0.2.7"
)

```

or import it in `Ammonite`:

```
import $ivy.`org.markushauck::mockitoscala:0.2.7`
```

Finally, extend
from
[MockitoSugar](https://github.com/markus1189/mockito-scala/blob/master/src/main/scala/org/markushauck/mockito/MockitoSugar.scala) trait
in your test suite or import all members of the companion object.

### Examples!

Please
see
[Examples](https://github.com/markus1189/mockito-scala/blob/master/src/test/scala/org/markushauck/mockito/Examples.scala) for
an overview of what is currently possible.

## Contributing

Contributions via GitHub pull requests are gladly accepted from their
original author. Along with any pull requests, please state that the
contribution is your original work and that you license the work to
the project under the project's open source license. Whether or not
you state this explicitly, by submitting any copyrighted material via
pull request, email, or other means you agree to license the material
under the project's open source license and warrant that you have the
legal authority to do so.

## License

This code is open source software licensed under
the
[Apache 2.0 License](http://apache.org/licenses/LICENSE-2.0.html).
