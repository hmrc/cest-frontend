/*
 * Copyright 2022 HM Revenue & Customs
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

package views.sections.exit

import assets.messages.{OfficeHolderMessages, SubHeadingMessages}
import controllers.sections.exit.routes
import forms.sections.exit.OfficeHolderFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.{YesNoViewBehavioursNew => YesNoViewBehaviours}
import views.html.sections.exit.OfficeHolderView

class OfficeHolderViewSpec extends YesNoViewBehaviours {

  object Selectors extends BaseCSSSelectors{
    val link = "#value > p:nth-child(3) > a"
  }

  val messageKeyPrefix = "worker.officeHolder"

  val form = new OfficeHolderFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[OfficeHolderView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "OfficeHolder view" must {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(OfficeHolderMessages.Worker.title, Some(SubHeadingMessages.officeHolder))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(OfficeHolderMessages.Worker.heading)
      }

      "have the correct page content" in {
        document.select(Selectors.p(1)).text mustBe OfficeHolderMessages.Worker.p1
        document.select(Selectors.p(2)).text mustBe OfficeHolderMessages.Worker.p2
      }

      "have the correct link" in {
        document.getElementsContainingText("new tab").attr("href") mustBe frontendAppConfig.officeHolderUrl
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe OfficeHolderMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe OfficeHolderMessages.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(OfficeHolderMessages.Hirer.title, Some(SubHeadingMessages.officeHolder))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(OfficeHolderMessages.Hirer.heading)
      }

      "have the correct page content" in {
        document.select(Selectors.p(1)).text mustBe OfficeHolderMessages.Hirer.p1
        document.select(Selectors.p(2)).text mustBe OfficeHolderMessages.Hirer.p2
      }

      "have the correct link" in {
        document.getElementsContainingText("new tab").attr("href") mustBe frontendAppConfig.officeHolderUrl
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe OfficeHolderMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe OfficeHolderMessages.no
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {
        document.title mustBe title(OfficeHolderMessages.Worker.title, Some(SubHeadingMessages.officeHolder))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(OfficeHolderMessages.Worker.heading)
      }

      "have the correct page content" in {
        document.select(Selectors.p(1)).text mustBe OfficeHolderMessages.Worker.p1
        document.select(Selectors.p(2)).text mustBe OfficeHolderMessages.Worker.p2
      }

      "have the correct link" in {
        document.getElementsContainingText("new tab").attr("href") mustBe frontendAppConfig.officeHolderUrl
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe OfficeHolderMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe OfficeHolderMessages.no
      }
    }
  }
}
