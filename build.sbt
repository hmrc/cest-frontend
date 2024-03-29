import play.sbt.routes.RoutesKeys
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, _}
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion

val appName: String = "cest-frontend"

lazy val appDependencies : Seq[ModuleID] = AppDependencies()
lazy val plugins : Seq[Plugins] = Seq(play.sbt.PlayScala)
lazy val playSettings : Seq[Setting[_]] = Seq.empty

lazy val scoverageSettings = {
    import scoverage.ScoverageKeys
    Seq(
        // Semicolon-separated list of regexs matching classes to exclude
        ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;.*filters.*;.*handlers.*;.*components.*;.*repositories.*;" +
          ".*BuildInfo.*;.*javascript.*;.*FrontendAuditConnector.*;.*Routes.*;.*GuiceInjector;" +
          ".*ControllerConfiguration;.*LanguageSwitchController;.*testonly.*;.*views.*;",
        ScoverageKeys.coverageMinimumStmtTotal := 80,
        ScoverageKeys.coverageFailOnMinimum := true,
        ScoverageKeys.coverageHighlighting := true,
        Test / parallelExecution := false
    )
}

lazy val microservice = Project(appName, file("."))
  .enablePlugins(Seq(play.sbt.PlayScala, SbtDistributablesPlugin) ++ plugins: _*)
  .settings(majorVersion := 1)
  .configs(IntegrationTest)
  .settings(scalaSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(scalaVersion := "2.13.8")
  .settings(scalacOptions += "-Ywarn-unused:-explicits,-implicits")
  .settings(playSettings ++ scoverageSettings: _*)
  .settings(
    RoutesKeys.routesImport += "models._",
    addTestReportOption(IntegrationTest, "int-test-reports"),
    inConfig(IntegrationTest)(Defaults.itSettings),
    libraryDependencies ++= appDependencies,
    retrieveManaged := true,
    Test / parallelExecution := true,
    Test / fork := true,
    IntegrationTest / Keys.fork :=  false,
    IntegrationTest / unmanagedSourceDirectories := (IntegrationTest / baseDirectory)(base => Seq(base / "it")).value,
    IntegrationTest / parallelExecution := false,
    // ***************
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s",
    // ***************
  )
  .settings(
    resolvers += Resolver.jcenterRepo
  )
  .disablePlugins(JUnitXmlReportPlugin)
