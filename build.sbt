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
    .settings(publishSettings)
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
  "-Xfatal-warnings",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-value-discard"
)

lazy val publishSettings = Seq(
  useGpg := true,
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false },
  organization := "org.markushauck",
  licenses += ("Apache 2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  homepage := Some(url("https://github.com/markus1189/mockito-scala")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/markus1189/mockito-scala"),
      "scm:git@github.com:markus1189/mockito-scala.git"
    )
  ),
  developers := List(
    Developer(
      id    = "markus1189",
      name  = "Markus Hauck",
      email = "markus1189@gmail.com",
      url   = url("https://github.com/markus1189")
    )
  ),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) {
      Some("snapshots" at nexus + "content/repositories/snapshots")
    } else {
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    }
  },
  publishArtifact in Test := false
)
