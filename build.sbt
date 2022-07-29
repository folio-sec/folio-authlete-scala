val authleteJavaVersion = "3.15"
val sttpVersion = "3.5.1"
val jacksonVersion = "2.13.1"
val slf4jVersion = "1.7.32"

val scalaTestVersion = "3.2.10"

lazy val baseSettings = Seq(
  scalaVersion := "2.13.8",
  organization := "com.folio",
  version := "1.0.1",
  scalacOptions := Seq(
    "-Xfatal-warnings",
    "-deprecation",
    "-unchecked",
    "-explaintypes",
    "-Xlint",
    "-feature"
  ),
  libraryDependencies ++= Seq(
    "com.authlete" % "authlete-java-common" % authleteJavaVersion,
    "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "slf4j-simple" % slf4jVersion % Test,
    "org.scalatest" %% "scalatest-funspec" % scalaTestVersion % Test,
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion % Test
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "folio-authlete-scala",
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
    publishTo := {
      if (isSnapshot.value) {
        Some("snapshots" at "https://repo.folio-sec.com/repository/maven-snapshots")
      } else {
        Some("releases" at "https://repo.folio-sec.com/repository/maven-releases")
      }
    }
  )
  .settings(baseSettings)
