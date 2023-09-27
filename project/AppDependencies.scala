import sbt._

object AppDependencies {

  val wireMockStandAloneVersion = "2.27.2"

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"               % "7.20.0-play-28",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"               % "1.3.0",
    "uk.gov.hmrc"       %% "http-caching-client"              % "10.0.0-play-28",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"    % "1.13.0-play-28",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"       % "7.22.0",
    "com.typesafe.play" %% "play-json-joda"                   % "2.10.1",
    "uk.gov.hmrc"       %% "digital-engagement-platform-chat" % "0.33.0-play-28"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"                  %% "bootstrap-test-play-28"      % "5.25.0",
    "uk.gov.hmrc"                  %% "service-integration-test"    % "1.3.0-play-28",
    "uk.gov.hmrc.mongo"            %% "hmrc-mongo-test-play-28"     % "1.1.0",
    "org.mockito"                  %  "mockito-core"                % "5.5.0",
    "org.scalatestplus"            %% "scalatestplus-mockito"       % "1.0.0-M2",
    "org.scalatestplus"            %% "scalacheck-1-15"             % "3.2.11.0",
    "org.jsoup"                    %  "jsoup"                       % "1.15.4",
    "org.scalamock"                %% "scalamock-scalatest-support" % "3.6.0",
    "com.github.tomakehurst"       %  "wiremock-standalone"         % wireMockStandAloneVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"        % "2.14.2"

  ).map(_ % "test, it")
  
  def apply(): Seq[ModuleID] = compile ++ test
}
