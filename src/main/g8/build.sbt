val zioVersion            = "2.1.18"
val zioJsonVersion        = "0.9.2"
val zioConfigVersion      = "4.0.7"
val zioLoggingVersion     = "2.5.3"
val logbackClassicVersion = "1.4.7"
val postgresqlVersion     = "42.6.0"
val testContainersVersion = "0.40.15"
val zioMockVersion        = "1.0.0-RC12"
val zioHttpVersion        = "3.11.2"
val quillVersion          = "4.8.6"
val apachePDFBoxVersion   = "3.0.7"

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

    libraryDependencySchemes += "dev.zio"       %% "zio-json"  % VersionScheme.Always,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
  .enablePlugins(JavaAppPackaging)
