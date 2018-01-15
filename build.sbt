enablePlugins(ScalaJSPlugin)

name := "sri-website"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "scalajs-react-interface" %%% "web-bundle" % "2017.12.0-SNAPSHOT"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-Xmacro-settings:classNameShrink=components"
)

scalaJSUseMainModuleInitializer := true

scalaJSUseMainModuleInitializer in Test := true

scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))

resolvers += Resolver.bintrayRepo("scalajs-react-interface", "maven")
resolvers += Resolver.bintrayRepo("scalajs-css", "maven")
resolvers += Resolver.bintrayRepo("scalajs-jest", "maven")

/** Custom tasks to generate launcher file in  CommonJSModule mode   */
val SJS_OUTPUT_PATH = "static/scalajs-output.js"

artifactPath in Compile in fastOptJS :=
  baseDirectory.value / SJS_OUTPUT_PATH

artifactPath in Compile in fullOptJS :=
  baseDirectory.value / SJS_OUTPUT_PATH

val dev = Def.taskKey[Unit]("Generate web output file via fastOptJS")

val prod = Def.taskKey[Unit]("Generate web output file via fullOptJS")

//lazy val web = config("Web")
//
//dev in web := {
//  (fastOptJS in Compile).value.data
//
//}
//
//prod in web := {
//  (fullOptJS in Compile).value.data
//}
