val scala3Version = "3.4.0"

// Rilevamento del sistema operativo
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

val javaFXVersion = "21.0.2"
// Lista delle dipendenze di JavaFX
val javafxBinaries = {
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % javaFXVersion classifier osName)
}

import scala.sys.process.*
// Rilevamento dell'architettura del sistema
val architecture = "uname -m".!!.trim match {
  case "arm64" => "aarch"
  case _ => "x86"
}
val reactiveMongoNativePartial: String => String = os => s"1.1.0-RC6-$os-$architecture-64"
// Versione di ReactiveMongo per il sistema operativo corrente
val reactiveMongoNativeVersion = osName match {
  case "linux" => reactiveMongoNativePartial(osName)
  case "mac" => reactiveMongoNativePartial("osx")
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "PPS-23-LambdaQuiz",
    version := "1.0",
    scalaVersion := scala3Version,
    assembly / mainClass := Some("it.unibo.pps.start"),
    assembly / assemblyJarName := s"lambdaquiz-1.0.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "substrate", "config", xs @ _*) => MergeStrategy.discard
      case PathList("module-info.class") => MergeStrategy.discard
      case PathList("reactivemongo", "io", "netty", "channel", "unix", xs @ _*) => MergeStrategy.first
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    },
    Compile / run / mainClass := Some("it.unibo.pps.start"),
    wartremoverErrors ++= Warts.unsafe,
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "21.0.0-R32",
      "ch.qos.logback" % "logback-classic" % "1.5.6",
      "org.reactivemongo" %% "reactivemongo" % "1.1.0-RC12",
      "org.reactivemongo" % "reactivemongo-shaded-native" % reactiveMongoNativeVersion,
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % "test"
    ) ++ javafxBinaries
  )
