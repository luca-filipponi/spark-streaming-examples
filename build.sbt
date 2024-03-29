import sbt.ExclusionRule
// give the user a nice default project!

val kafkaVersion = "1.0.0"


lazy val root = (project in file(".")).

  settings(
    inThisBuild(List(
      organization := "com.filipponi",
      scalaVersion := "2.11.12"
    )),
    name := "data-streaming",
    version := "0.0.1",

    sparkVersion := "2.4.1",
    sparkComponents := Seq(),

    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    parallelExecution in Test := false,
    fork := true,

    coverageHighlighting := true,

    libraryDependencies ++= Seq(

      "org.apache.spark" %% "spark-streaming" % "2.4.1" % "provided",
      "org.apache.spark" %% "spark-sql" % "2.4.1" % "provided",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.1" % "provided",

      "org.scalatest" %% "scalatest" % "3.0.1" % Test,
      "org.scalacheck" %% "scalacheck" % "1.13.4" % Test,
//      "net.manub" %% "scalatest-embedded-kafka" % kafkaVersion % Test,
      "org.apache.kafka" % "kafka-clients" % "1.0.0" % Test,
      "com.holdenkarau" %% "spark-testing-base" % "2.4.0_0.11.0" % Test
    ),

    // uses compile classpath for the run task, including "provided" jar (cf http://stackoverflow.com/a/21803413/3827)
    run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated,

    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    pomIncludeRepository := { x => false },

   resolvers ++= Seq(
      "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
      Resolver.sonatypeRepo("public")
    ),

    pomIncludeRepository := { x => false },

    // publish settings
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    }
  )
