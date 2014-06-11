name := "threes-brain"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
     "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3",
     "com.netflix.rxjava" % "rxjava-scala" % "0.19.0"
)