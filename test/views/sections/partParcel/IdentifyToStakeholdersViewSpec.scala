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

package views.sections.partParcel

import assets.messages.{IdentifyToStakeholdersMessages, SubHeadingMessages}
import forms.sections.partAndParcel.IdentifyToStakeholdersFormProvider
import models.NormalMode
import models.sections.partAndParcel.IdentifyToStakeholders
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.ViewBehavioursNew
import views.html.sections.partParcel.IdentifyToStakeholdersView

class IdentifyToStakeholdersViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.identifyToStakeholders"

  val form = new IdentifyToStakeholdersFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[IdentifyToStakeholdersView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "IdentifyToStakeholders view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(IdentifyToStakeholdersMessages.Worker.title, Some(SubHeadingMessages.partAndParcel))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (IdentifyToStakeholdersMessages.Worker.heading)
      }

      "have the correct radio options" in {
        document.select(Selectors.multichoice(1)).text mustBe IdentifyToStakeholdersMessages.Worker.workForEndClient
        document.select(Selectors.multichoice(2)).text mustBe IdentifyToStakeholdersMessages.Worker.workAsIndependent
        document.select(Selectors.multichoice(3)).text mustBe IdentifyToStakeholdersMessages.Worker.workAsBusiness
        document.select(Selectors.multichoice(3)).text mustBe IdentifyToStakeholdersMessages.Worker.workAsBusiness
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(IdentifyToStakeholdersMessages.Hirer.title, Some(SubHeadingMessages.partAndParcel))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (IdentifyToStakeholdersMessages.Hirer.heading)
      }

      "have the correct radio options" in {
        document.select(Selectors.multichoice(1)).text mustBe IdentifyToStakeholdersMessages.Hirer.workForEndClient
        document.select(Selectors.multichoice(2)).text mustBe IdentifyToStakeholdersMessages.Hirer.workAsIndependent
        document.select(Selectors.multichoice(3)).text mustBe IdentifyToStakeholdersMessages.Hirer.workAsBusiness
        document.select(Selectors.multichoice(4)).text mustBe IdentifyToStakeholdersMessages.Hirer.wouldNotHappen
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {
        document.title mustBe title(IdentifyToStakeholdersMessages.Worker.title, Some(SubHeadingMessages.partAndParcel))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (IdentifyToStakeholdersMessages.Worker.heading)
      }

      "have the correct radio options" in {
        document.select(Selectors.multichoice(1)).text mustBe IdentifyToStakeholdersMessages.Worker.workForEndClient
        document.select(Selectors.multichoice(2)).text mustBe IdentifyToStakeholdersMessages.Worker.workAsIndependent
        document.select(Selectors.multichoice(3)).text mustBe IdentifyToStakeholdersMessages.Worker.workAsBusiness
        document.select(Selectors.multichoice(4)).text mustBe IdentifyToStakeholdersMessages.Worker.wouldNotHappen
      }
    }
  }

  "IdentifyToStakeholders view" when {
    "rendered" must {
      "contain radio buttons for the value" in {
        val doc = asDocument(createViewUsingForm(form))
        for (option <- IdentifyToStakeholders.options) {
          assertContainsRadioButton(doc, idHelper(IdentifyToStakeholders.options, option), "value", option.value, isChecked = false)
        }
      }
    }

    for(option <- IdentifyToStakeholders.options) {
      s"rendered with a value of '${option.value}'" must {
        s"have the '${option.value}' radio button selected" in {
          val doc = asDocument(createViewUsingForm(form.bind(Map("value" -> s"${option.value}"))))
          assertContainsRadioButton(doc, idHelper(IdentifyToStakeholders.options, option), "value", option.value, isChecked = true)

          for(unselectedOption <- IdentifyToStakeholders.options.filterNot(o => o == option)) {
            assertContainsRadioButton(doc, idHelper(IdentifyToStakeholders.options, unselectedOption), "value", unselectedOption.value, isChecked = false)
          }
        }
      }
    }
  }
}
