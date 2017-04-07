lazy val mockitoScala = {
  project
    .in(file("."))
    .enablePlugins(GitVersioning)
    .settings(
      scalaVersion := "2.12.1",
      scalacOptions ++= customScalacOptions,
      libraryDependencies ++= libs,
      libraryDependencies ++= testLibs,
      git.useGitDescribe := true
    )
}

lazy val libs = {
  object Version {
    final val mockito = "2.7.21"
  }

  Vector(
    "org.mockito" % "mockito-core" % Version.mockito
  )
}

lazy val testLibs = {
  object Version {
    final val scalaTest = "3.0.1"
  }

  Vector(
    "org.scalatest" %% "scalatest" % Version.scalaTest
  ).map(_ % Test)
}

lazy val customScalacOptions = Seq(
  "-deprecation",
  "-unchecked",
  "-language:_",
  // format: off
  "-encoding", "UTF-8",
  // format: on
  "-Xlint",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-value-discard"
)
