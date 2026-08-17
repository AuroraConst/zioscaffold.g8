val zioVersion            = "2.1.26"
val zioJsonVersion        = "0.10.0"
val zioConfigVersion      = "4.0.8"
val zioLoggingVersion     = "2.5.3"
val logbackClassicVersion = "1.4.7"
val postgresqlVersion     = "42.7.13"
val testContainersVersion = "0.44.1"
val zioMockVersion        = "1.0.0-RC11" //deprecated The ZIO ecosystem has largely shifted away from heavy macro-based mock frame abstractions (zio-mock) toward ZIO Test native testing patterns. ZIO 2.x emphasizes using standard Scala features—such as manual test layers, stubbing dependencies with plain case classes, or utilizing standard testing libraries (e.g., Mockito, ScalaMock)—over complex expectation DSLs
val zioHttpVersion        = "3.11.3" 
val quillVersion          = "4.8.6"
val apachePDFBoxVersion   = "3.0.5"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "3.7.2"
      )
    ),
    scalacOptions ++=  Seq(
      "-Wunused:all",
    "-Werror:false"    ), 

    name           := "pdfwriter",
    run / fork := true, // Enable forking to run the zio application so that when it terminates it does not exit the sbt shell

    libraryDependencies ++=   Seq(
     "com.github.pathikrit" %% "better-files" % "3.9.2",
     "com.github.librepdf"  % "openpdf"     % "2.2.4",
       //pdf
      "org.apache.pdfbox" % "pdfbox" % apachePDFBoxVersion

   ),

    libraryDependencies ++= Seq(
      "io.getquill"   %% "quill-jdbc-zio"      % quillVersion excludeAll (
        ExclusionRule(organization = "org.scala-lang.modules")
      ),
      "org.postgresql" % "postgresql"          % postgresqlVersion,
      "dev.zio"       %% "zio"                 % zioVersion,
      "dev.zio"       %% "zio-streams"         % zioVersion,
      "dev.zio"       %% "zio-http"            % zioHttpVersion,
      "dev.zio"       %% "zio-config"          % zioConfigVersion,
      "dev.zio"       %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio"       %% "zio-config-magnolia" % zioConfigVersion,
      "ch.qos.logback" % "logback-classic"     % logbackClassicVersion,
      "dev.zio"       %% "zio-json"            % zioJsonVersion,

    
      // logging
      "dev.zio"       %% "zio-logging"       % zioLoggingVersion,
      "dev.zio"       %% "zio-logging-slf4j" % zioLoggingVersion,
      "ch.qos.logback" % "logback-classic"   % logbackClassicVersion,

      // test
      "dev.zio"      %% "zio-test"                        % zioVersion            % Test,
      "dev.zio"      %% "zio-test-sbt"                    % zioVersion            % Test,
      "dev.zio"      %% "zio-test-junit"                  % zioVersion            % Test,
      "dev.zio"      %% "zio-mock"                        % zioMockVersion        % Test,
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,
      "dev.zio"      %% "zio-test-magnolia"               % zioVersion            % Test,
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
  .enablePlugins(JavaAppPackaging)
