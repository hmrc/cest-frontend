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

import assets.messages.WorkerUsingIntermediaryMessages
import config.featureSwitch.FeatureSwitching
import forms.sections.setup.WorkerUsingIntermediaryFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.ViewBehavioursNew
import views.html.sections.setup.WorkerUsingIntermediaryView

class WorkerTypeViewSpec extends ViewBehavioursNew with FeatureSwitching {

  object Selectors extends BaseCSSSelectors {
    val p1 = "#content form p:nth-of-type(1)"
    val p2 = "#content form p:nth-of-type(2)"
  }

  val messageKeyPrefix: String = "worker.workerUsingIntermediary"

  val form = new WorkerUsingIntermediaryFormProvider()()(workerFakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[WorkerUsingIntermediaryView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "WorkerType view" must {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = false)

    behave like pageWithBackLink(createView)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))
      "have the correct title" in {

        document.title mustBe title(WorkerUsingIntermediaryMessages.Worker.title)
      }

      "have the correct heading" in {

        document.getElementsByClass("hmrc-page-heading").text() must include(WorkerUsingIntermediaryMessages.Worker.heading)
      }

      "have the correct p1" in {

        document.getElementsByTag("p").text must include(WorkerUsingIntermediaryMessages.Worker.p1)
      }

      "have the correct p2" in {

        document.getElementsByTag("p").text must include(WorkerUsingIntermediaryMessages.Worker.p2)
      }

      "have the correct radio option messages" in {

        document.select(Selectors.multichoice(1)).text mustBe WorkerUsingIntermediaryMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe WorkerUsingIntermediaryMessages.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {


        document.title mustBe title(WorkerUsingIntermediaryMessages.Hirer.title)
      }

      "have the correct heading" in {

        document.select(Selectors.heading).text must include(WorkerUsingIntermediaryMessages.Hirer.heading)
      }

      "have the correct p1" in {
        document.getElementsByTag("p").text() must include(WorkerUsingIntermediaryMessages.Hirer.p1)
      }

      "have the correct p2" in {
        document.getElementsByTag("p").text() must include(WorkerUsingIntermediaryMessages.Hirer.p2)
      }

      "have the correct radio option messages" in {

        document.select(Selectors.multichoice(1)).text mustBe WorkerUsingIntermediaryMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe WorkerUsingIntermediaryMessages.no
      }
    }
  }

  "WorkerType view" when {
    "rendered" must {
      "contain radio buttons for the value" in {

        val doc = asDocument(createViewUsingForm(form))
        assertContainsRadioButton(doc, "value", "value", "true", isChecked = false)
        assertContainsRadioButton(doc, "value-2", "value", "false", isChecked = false)
      }
    }

    s"rendered with a value of 'true'" must {
      s"have the 'true' radio button selected" in {

        val doc = asDocument(createViewUsingForm(form.bind(Map("value" -> s"true"))))
        assertContainsRadioButton(doc, "value", "value", "true", isChecked = true)
        assertContainsRadioButton(doc, "value-2", "value", "false", isChecked = false)
      }
    }
  }
}
