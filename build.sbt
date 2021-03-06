// Project template

// Supported OS/JDK combos:
//   - Windows: Oracle JDK 8, 10, 11
//   - Mac: OpenJDK 8, 10, 11 (not tested on Mac)
//   - Linux: OpenJDK 8, 10, 11

// You can configure stuff with environment variables:
// JAVAFX_HOME -- where to look for JavaFX (10/11)

// Project name
name := "fractalexplorer"

// organization name
organization := "fi.utu"

version := "1.0"

// project description
description := "Fractal explorer"

// force the java version by typing it here (remove the comment)
val force_javaVersion = None // Some(10)

// force the javafx version by typing it here (remove the comment)
val force_javaFxVersion = None // Some(11)

// the script will automatically pick the best libraries for you
val javaVersionNum = force_javaVersion getOrElse {
  var sysVersion = System.getProperty("java.version")

  if (sysVersion.startsWith("1."))
    sysVersion = sysVersion.drop(2)

  sysVersion.split('.').head.toInt
}

val javaVersionString = javaVersionNum match {
  case 7 => "1.7"
  case 8 => "1.8"
  case 10 => "10"
  case 11 => "11"
  case _ => throw new Exception("Unsupported Java version. Use 7/8/10/11")
}

javacOptions ++= Seq("-source", javaVersionString, "-target", javaVersionString, "-encoding", "utf8", "-Xlint:unchecked")

compileOrder := CompileOrder.JavaThenScala

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

fork in Global := true

val javafx_version = force_javaFxVersion getOrElse (javaVersionNum match {
  case 7 => 7
  case 8 => 8
  case 10 | 11 => 11
  case _ => throw new Exception("Unsupported JavaFX version. Use 7/8/10/11.")
})

// JAVA_HOME location
val javaHomeDir = {
  val path = try {
    scala.sys.env("JAVA_HOME")
  } catch {
    case _: Throwable => System.getProperty("java.home") // not set -> ask from current JVM
  }

  val f = file(path)
  if (!f.exists()) throw new Exception("JAVA_HOME points to a non-existent directory! Please refer to the course instructions!")
  f
}

val osName: SettingKey[String] = SettingKey[String]("osName")

osName := (System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
})

def legacyJavaFX(jfxVersion: Int, badVersions: Seq[Int]) = {
  val searchDirs = Seq(
    "/jre/lib/jfxrt.jar", // OpenJDK 7
    "/jre/lib/ext/jfxrt.jar", // OpenJDK 8
    "/lib/ext/jfxrt.jar" // Windows & Oracle Java 8
  )

  val javaFxJAR = searchDirs.map{ searchDir => file(javaHomeDir + searchDir) }.find{ _.exists() }

  javaFxJAR.getOrElse {
    val p = javaHomeDir.toString
    throw new Exception("Java FX runtime not installed in [" + p + "]!" +
      (if (badVersions.exists { v => p.contains("jdk-" + v) }) " Did you try to run JavaFX " + jfxVersion + " with JDK " + badVersions.sorted.mkString("/") + "?" else "") +
      " Please refer to the course instructions!"
    )
  }
}

val javaFxPath = Def.taskKey[File]("OpenJFX fetcher")
javaFxPath := {
  try {
    val javaFxHome = file(scala.sys.env("JAVAFX_HOME"))
    if (!javaFxHome.exists()) throw new Exception("JAVAFX_HOME points to a non-existent directory! Please refer to the course instructions!")
    println("Using OpenJFX from " + javaFxHome)
    javaFxHome
  }
  catch {
    case _: Throwable =>
      println("Using local OpenJFX")
      val dir = baseDirectory.value / "openjfx"
      if (!dir.exists()) java.nio.file.Files.createDirectory(dir.toPath)

      val sdkURL = "http://download2.gluonhq.com/openjfx/11/openjfx-11_" + (osName.value match {
        case "linux" => "linux"
        case "mac"   => "osx"
        case "win"   => "windows"
      }) + "-x64_bin-sdk.zip"

      try {
        val testDir = dir / "all.ok"
        if (!testDir.exists()) {
          println("Fetching OpenJFX from "+sdkURL+"..")
          IO.unzipURL(new URL(sdkURL), dir)
          java.nio.file.Files.createDirectory(testDir.toPath)
          println("Fetching OpenJFX done.")
        } else {
          println("Local OpenJFX found from "+dir)
        }

        dir
      }
      catch {
        case t: Throwable => throw new Exception("Could not load OpenJFX! Reason:" + t.getMessage)
      }
  }
}

javafx_version match {
  case 7 =>
    // TODO libraryDependencies
    Seq(unmanagedJars in Compile += Attributed.blank(legacyJavaFX(8, Seq(10, 11, 8))))
  case 8 =>
    Seq(
      libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
      unmanagedJars in Compile += Attributed.blank(legacyJavaFX(8, Seq(10, 11, 7))),
    )
  case 11 =>
    Seq(
      javaOptions ++= Seq(
        "--module-path", (javaFxPath.value / "javafx-sdk-11" / "lib").toString,
        "--add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web"),

      libraryDependencies ++= Seq(
        "org.openjfx" % "javafx-base" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-controls" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-fxml" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-graphics" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-media" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-swing" % "11-ea+25" classifier osName.value,
        "org.openjfx" % "javafx-web" % "11-ea+25" classifier osName.value
      )
    )
}

import xml.transform.{RewriteRule, RuleTransformer}
import xml.{Node,NodeSeq,Elem}

pomPostProcess := {
  def rule(f: PartialFunction[Node, NodeSeq]): RewriteRule = new RewriteRule {
    override def transform(n: Node) = if (f.isDefinedAt(n)) f(n) else n
  }

  def depName(e: Elem) =
    Seq(e).filter(_.label == "dependency").flatMap(_.child).filter{_.label == "artifactId" }.flatMap(_.child).mkString

  def toolVersions =
    <properties>
      <maven.compiler.source>{javaVersionString}</maven.compiler.source>
      <maven.compiler.target>{javaVersionString}</maven.compiler.target>
    </properties>

  new RuleTransformer(rule {
    case e: Elem if depName(e) == "jupiter-interface" => println("Skipped "+depName(e)); NodeSeq.Empty
    case e: Elem if e.label == "organization" => NodeSeq.seqToNodeSeq(Seq(e, toolVersions))
  })
}

val javaVersion = taskKey[Unit]("Prints the Java version.")

javaVersion := { println("SBT uses Java SDK located at "+System.getProperty("java.home")) }

val netbeans = taskKey[Unit]("Makes a Netbeans compatible pom.xml.")
val eclipse  = taskKey[Unit]("Makes a Eclipse compatible pom.xml.")

netbeans := {
  println("Tehd????n Netbeans-yhteensopiva pom.xml!")
  IO.copyFile(makePom.value, file("pom.xml"))
}

eclipse := {
  println("Tehd????n Eclipse-yhteensopiva pom.xml!")
  IO.copyFile(makePom.value, file("pom.xml"))
}

publishTo := Some(Resolver.file("file", new File("/tmp/repository")))
