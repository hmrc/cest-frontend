/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config

import com.google.inject.{Inject, Singleton}
import config.featureSwitch.{DecisionServiceVersionFeature, FeatureSwitching}
import controllers.routes
import play.api.Environment
import play.api.i18n.Lang
import play.api.mvc.{Call, Request}
import uk.gov.hmrc.play.bootstrap.binders.SafeRedirectUrl
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class FrontendAppConfig @Inject() (environment: Environment, val servicesConfig: ServicesConfig) {

  private lazy val contactHost = servicesConfig.getString("contact-frontend.host")
  private val contactFormServiceIdentifier = "off-payroll"

  lazy val reportAProblemPartialUrl = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  lazy val reportAProblemNonJSUrl = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"

  private lazy val exitSurveyBaseUrl = servicesConfig.getString("feedback-frontend.host") + servicesConfig.getString("feedback-frontend.url")
  lazy val exitSurveyUrl = s"$exitSurveyBaseUrl/$contactFormServiceIdentifier"

  private def requestPath(implicit request: Request[_]) = SafeRedirectUrl(host + request.path).encodedUrl
  def feedbackUrl(implicit request: Request[_]): String =
    s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier&backUrl=$requestPath"
  def reportAccessibilityIssueUrl(problemPageUri: String)(implicit request: Request[_]): String =
    s"$contactHost/contact/accessibility-unauthenticated?service=" +
      s"$contactFormServiceIdentifier&userAction=${SafeRedirectUrl(host + problemPageUri).encodedUrl}"

  lazy val loginUrl: String = servicesConfig.getString("urls.login")
  lazy val loginContinueUrl: String = servicesConfig.getString("urls.loginContinue")

  lazy val host: String = servicesConfig.getString("host")

  def languageMap: Map[String, Lang] = Map(
    "english" -> Lang("en"),
    "cymraeg" -> Lang("cy"))
  def routeToSwitchLanguage: String => Call = (lang: String) => routes.LanguageSwitchController.switchToLanguage(lang)

  lazy val mongoTtl: Int = servicesConfig.getInt("mongodb.timeToLiveInSeconds")

  def decisionVersion: String = FeatureSwitching.getValue(DecisionServiceVersionFeature)(this)

  lazy val pdfGeneratorService: String = servicesConfig.baseUrl("pdf-generator-service")

  lazy val timeoutPeriod: Int = servicesConfig.getInt("timeout.period")
  lazy val timeoutCountdown: Int = servicesConfig.getInt("timeout.countdown")

  lazy val govUkStartPageUrl: String = servicesConfig.getString("urls.govUkStartPage")
  lazy val employmentStatusManualUrl: String = servicesConfig.getString("urls.employmentStatusManual")
  lazy val employmentStatusManualChapter5Url: String = servicesConfig.getString("urls.employmentStatusManualChapter5")
  lazy val employmentStatusManualESM0527Url: String = servicesConfig.getString("urls.employmentStatusManualESM0527")
  lazy val employmentStatusManualESM0521Url: String = servicesConfig.getString("urls.employmentStatusManualESM0521")
  lazy val employmentStatusManualESM11160Url: String = servicesConfig.getString("urls.employmentStatusManualESM11160")
  lazy val officeHolderUrl: String = servicesConfig.getString("urls.officeHolder")
  lazy val understandingOffPayrollUrl: String = servicesConfig.getString("urls.understandingOffPayroll")
  lazy val feePayerResponsibilitiesUrl: String = servicesConfig.getString("urls.feePayerResponsibilities")
  lazy val payeForEmployersUrl: String = servicesConfig.getString("urls.payeForEmployers")
  lazy val govukAccessibilityStatementUrl: String = servicesConfig.getString("urls.govukAccessibilityStatement")
  lazy val abilityNetUrl: String = servicesConfig.getString("urls.abilityNet")
  lazy val wcagUrl: String = servicesConfig.getString("urls.wcag")
  lazy val eassUrl: String = servicesConfig.getString("urls.eass")
  lazy val ecniUrl: String = servicesConfig.getString("urls.ecni")
  lazy val hmrcAdditionalNeedsUrl: String = servicesConfig.getString("urls.hmrcAdditionalNeeds")

}
