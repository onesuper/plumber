/**
 * Created by baihe on 16/4/8.
 */

import Dependencies._
import sbt.Keys._
import sbt._
import Resolvers._

object PlumberBuild extends Build {

  lazy val commonSettings = Seq(
    organization := "org.argonath",
    version := "0.1.0",
    scalaVersion := "2.10.6",

    resolvers ++= customizedResolvers
  )

  externalPom()

  lazy val config = Project(id = "plumber-config", base = file("./plumber-config")).
    settings(commonSettings: _*).
    settings(
      name := "plumber-config",
      libraryDependencies ++= confDependencies
    )

  lazy val core = Project(id = "plumber-core", base = file("./plumber-core")).
    settings(commonSettings: _*).
    settings(
      name := "plumber-core",
      libraryDependencies ++= coreDependencies
    )

  lazy val runtime = Project(id = "plumber-runtime", base = file("./plumber-runtime")).
    dependsOn(config, core).
    settings(commonSettings: _*).
    settings(
      name := "plumber-runtime",
      libraryDependencies ++= runtimeDependencies
    )

  lazy val plumber = Project(id = "plumber", base = file(".")).
    dependsOn(config, core, runtime).
    aggregate(config, core, runtime).
    settings(
      name := "plumber",
      libraryDependencies ++= plumberDependencies
    )
}
