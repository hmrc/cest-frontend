import sbt._

object AppDependencies {

  val wireMockVersion = "2.32.0"
  val wireMockStandAloneVersion = "2.27.2"

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"               % "6.0.0-play-28",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"               % "0.74.0",
    "uk.gov.hmrc"       %% "govuk-template"                   % "5.78.0-play-28",
    "uk.gov.hmrc"       %% "play-ui"                          % "9.11.0-play-28",
    "uk.gov.hmrc"       %% "http-caching-client"              % "10.0.0-play-28",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"    % "1.12.0-play-28",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"       % "5.25.0",
    "com.typesafe.play" %% "play-json-joda"                   % "2.9.3",
    "uk.gov.hmrc"       %% "digital-engagement-platform-chat" % "0.32.0-play-28"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"                  %% "bootstrap-test-play-28"      % "5.25.0",
    "uk.gov.hmrc"                  %% "service-integration-test"    % "1.3.0-play-28",
    "uk.gov.hmrc.mongo"            %% "hmrc-mongo-test-play-28"     % "0.74.0",
    "org.mockito"                  %  "mockito-core"                % "4.11.0",
    "org.scalatestplus"            %% "scalatestplus-mockito"       % "1.0.0-M2",
    "org.scalatestplus"            %% "scalacheck-1-15"             % "3.2.11.0",
    "org.jsoup"                    %  "jsoup"                       % "1.15.3",
    "org.scalamock"                %% "scalamock-scalatest-support" % "3.6.0",
    "com.github.tomakehurst"       %  "wiremock-standalone"         % wireMockStandAloneVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"        % "2.14.1"

  ).map(_ % "test, it")
  
  def apply(): Seq[ModuleID] = compile ++ test
}
