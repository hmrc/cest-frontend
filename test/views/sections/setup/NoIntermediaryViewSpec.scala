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

package views.sections.setup

import assets.messages.NoIntermediaryMessages
import play.api.mvc.Request
import views.behaviours.ViewBehavioursNew
import views.html.sections.setup.NoIntermediaryView

class NoIntermediaryViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors {
    val p1 = "#main-content p:nth-child(2)"
    val p2 = "#main-content p:nth-child(3)"
    val startAgain = "#start-again"
    val understandingOffPayroll = "#understanding-off-payroll"
  }

  val messageKeyPrefix = "worker.noIntermediaryResult"

  val view = injector.instanceOf[NoIntermediaryView]

  def createView = () => view(controllers.routes.StartAgainController.redirectToDisclaimer()
)(workerFakeRequest, messages, frontendAppConfig)
  def createViewWithRequest =
    (req: Request[_]) => view(controllers.routes.StartAgainController.redirectToDisclaimer()
)(req, messages, frontendAppConfig)

  "no intermediary view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = false)

    behave like pageWithBackLink(createView)

    "for worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeDataRequest))

      "have the correct title" in {
        document.title mustBe title(NoIntermediaryMessages.Worker.title)
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text mustBe NoIntermediaryMessages.Worker.heading
      }

      "have the correct p1" in {
        document.select(Selectors.p1).text mustBe NoIntermediaryMessages.Worker.p1
        document.select(Selectors.understandingOffPayroll).attr("href") mustBe frontendAppConfig.understandingOffPayrollUrl
      }

      "have the correct p2" in {
        document.select(Selectors.p2).text mustBe NoIntermediaryMessages.Worker.p2
      }

      "have the correct start again link" in {
        document.select(Selectors.startAgain).attr("href") mustBe controllers.routes.StartAgainController.redirectToDisclaimer().url

      }
    }

    "for hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeDataRequest))

      "have the correct title" in {
        document.title mustBe title(NoIntermediaryMessages.Hirer.title)
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text mustBe NoIntermediaryMessages.Hirer.heading
      }

      "have the correct p1" in {
        document.select(Selectors.p1).text mustBe NoIntermediaryMessages.Hirer.p1
        document.select(Selectors.understandingOffPayroll).attr("href") mustBe frontendAppConfig.understandingOffPayrollUrl
      }

      "have the correct p2" in {
        document.select(Selectors.p2).text mustBe NoIntermediaryMessages.Hirer.p2
      }

      "have the correct start again link" in {
        document.select(Selectors.startAgain).attr("href") mustBe controllers.routes.StartAgainController.redirectToDisclaimer().url

      }
    }
  }
}
