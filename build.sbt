import sbt.Keys.libraryDependencies
import sbt._

val scala3Version = "3.3.1"
val scalaFXVersion = "16.0.0-R24"

lazy val root = project
  .in(file("."))
  .settings(
    name := "wordle1",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test",
    libraryDependencies +="org.scalafx" %% "scalafx" % scalaFXVersion,
      libraryDependencies ++= {
          // Determine OS version of JavaFX binaries
          lazy val osName = System.getProperty("os.name") match {
              case n if n.startsWith("Linux") => "linux"
              case n if n.startsWith("Mac") => "mac"
              case n if n.startsWith("Windows") => "win"
              case _ => throw new Exception("Unknown platform!")
          }
          Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
            .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
      },
      libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0").cross(CrossVersion.for3Use2_13),
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "com.google.inject" % "guice" % "7.0.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.3",
      libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"
  )