ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

//Add scalafx dependency
libraryDependencies += "org.scalafx" %% "scalafx-extras" % "0.7.0"
libraryDependencies += "org.scalafx" % "scalafx_3" % "19.0.0-R30"
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
//add javafx dependency
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

//Add scala test dependency
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"

lazy val root = (project in file("."))
  .settings(
    name := "killersodoku-QuanHoang"
  )



