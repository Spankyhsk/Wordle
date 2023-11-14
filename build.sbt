val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "wordle1",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
  )