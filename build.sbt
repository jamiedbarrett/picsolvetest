name := "picsolvetest"

version := "1.0"

lazy val `picsolvetest` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws,  specs2 % Test, evolutions,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.mockito" % "mockito-all" % "1.10.19",
  "org.scalatest" %% "scalatest" % "2.2.5",
  "org.scalatestplus" %% "play" % "1.4.0-M4")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator