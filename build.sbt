lazy val mockitoScala = {
  project
    .in(file("."))
    .enablePlugins(GitVersioning)
    .settings(
      scalaVersion := "2.12.1",
      scalacOptions ++= customScalacOptions,
      libraryDependencies ++= Seq(
        library.mockitoCore,
        library.scalaTest % Test
      )
    )
}

lazy val library = new {
  object Version {
    final val mockito = "2.7.21"
    final val scalaTest = "3.0.1"
  }

  val mockitoCore = "org.mockito" % "mockito-core" % Version.mockito
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
}

lazy val customScalacOptions = Seq(
  "-deprecation",
  "-unchecked",
  "-language:_",
  "-encoding", "UTF-8",
  "-Xlint",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-value-discard"
)
