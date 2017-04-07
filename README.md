## Mockito Sugar for Scala

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

To use the provided sugar, mixing
the
[MockitoSugar](https://github.com/markus1189/mockito-scala/blob/master/src/main/scala/de/codecentric/mockito/MockitoSugar.scala) trait
into your suite or use the companion object that extends from it.

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
