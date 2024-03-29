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

package views.sections.personalService

import assets.messages.{ArrangedSubstituteMessages, SubHeadingMessages}
import forms.sections.personalService.ArrangedSubstituteFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.ViewBehavioursNew
import views.html.sections.personalService.ArrangedSubstituteView

class ArrangedSubstituteViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.arrangedSubstitute"

  val form = new ArrangedSubstituteFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[ArrangedSubstituteView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "ArrangedSubstitute view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.Worker.title, Some(SubHeadingMessages.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(ArrangedSubstituteMessages.Worker.heading)
      }

      "have the correct content" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.Worker.p1
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.Worker.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.Worker.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.Worker.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.Hirer.title, Some(SubHeadingMessages.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(ArrangedSubstituteMessages.Hirer.heading)
      }

      "have the correct content" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.Hirer.p1
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.Hirer.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.Hirer.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.Hirer.no
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.Worker.title, Some(SubHeadingMessages.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(ArrangedSubstituteMessages.Worker.heading)
      }

      "have the correct content" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.Worker.p1
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.Worker.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.Worker.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.Worker.no
      }
    }
  }
}
