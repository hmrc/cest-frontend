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

package views.sections.businessOnOwnAccount

import assets.messages.SimilarWorkOtherClientsMessages
import forms.sections.businessOnOwnAccount.SimilarWorkOtherClientsFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.businessOnOwnAccount.SimilarWorkOtherClientsView

class SimilarWorkOtherClientsViewSpec extends YesNoViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.similarWorkOtherClients"

  val form = new SimilarWorkOtherClientsFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[SimilarWorkOtherClientsView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "SimilarWorkOtherClientsView" when {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "the WhoAreYou is Worker" must {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(SimilarWorkOtherClientsMessages.Worker.title, Some(SimilarWorkOtherClientsMessages.Worker.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(SimilarWorkOtherClientsMessages.Worker.heading)
      }

      "have the correct content" in {
        document.select(Selectors.p(1)).text mustBe SimilarWorkOtherClientsMessages.Worker.p1
        document.select(Selectors.p(2)).text mustBe SimilarWorkOtherClientsMessages.Worker.p2

      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe SimilarWorkOtherClientsMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe SimilarWorkOtherClientsMessages.no
      }
    }

    "the WhoAreYou is Hirer" must {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(SimilarWorkOtherClientsMessages.Hirer.title, Some(SimilarWorkOtherClientsMessages.Hirer.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(SimilarWorkOtherClientsMessages.Hirer.heading)
      }

      "have the correct content" in {
        document.select(Selectors.p(1)).text mustBe SimilarWorkOtherClientsMessages.Hirer.p1
        document.select(Selectors.p(2)).text mustBe SimilarWorkOtherClientsMessages.Hirer.p2

      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe SimilarWorkOtherClientsMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe SimilarWorkOtherClientsMessages.no
      }
    }
  }
}
