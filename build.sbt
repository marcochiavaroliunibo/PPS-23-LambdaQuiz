val scala3Version = "3.4.0"

// Determine OS version for JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

val javafxBinaries = {
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "21" classifier osName)
}

val reactiveMongoNativePartial: String => String = os => s"1.1.0-RC6-$os-x86-64"
val reactiveMongoNativeVersion = osName match {
  case "linux" => reactiveMongoNativePartial(osName)
  case "mac" => reactiveMongoNativePartial("osx")
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "PPS-23-LambdaQuiz",
    version := "0.1",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "21.0.0-R32",
      "ch.qos.logback" % "logback-classic" % "1.5.3",
      "org.reactivemongo" %% "reactivemongo" % "1.1.0-RC12" % "provided",
      "org.reactivemongo" % "reactivemongo-shaded-native" % reactiveMongoNativeVersion,
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % "test"
    ) ++ javafxBinaries
  )
