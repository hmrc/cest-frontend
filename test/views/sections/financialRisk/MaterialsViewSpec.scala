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

package views.sections.financialRisk

import assets.messages.{MaterialsMessages, SubHeadingMessages}
import forms.sections.financialRisk.MaterialsFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.financialRisk.MaterialsView

class MaterialsViewSpec extends YesNoViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.materials"

  val form = new MaterialsFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[MaterialsView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "Materials view" must {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(MaterialsMessages.Worker.title, Some(SubHeadingMessages.financialRisk))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (MaterialsMessages.Worker.heading)
      }

      "have the correct text" in {
        document.select(Selectors.p(1)).text mustBe MaterialsMessages.Worker.p1
        document.select(Selectors.p(2)).text mustBe MaterialsMessages.Worker.p2
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe MaterialsMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe MaterialsMessages.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(MaterialsMessages.Hirer.title, Some(SubHeadingMessages.financialRisk))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (MaterialsMessages.Hirer.heading)
      }

      "have the correct text" in {
        document.select(Selectors.p(1)).text mustBe MaterialsMessages.Hirer.p1
        document.select(Selectors.p(2)).text mustBe MaterialsMessages.Hirer.p2
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe MaterialsMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe MaterialsMessages.no
      }
    }
  }
}
