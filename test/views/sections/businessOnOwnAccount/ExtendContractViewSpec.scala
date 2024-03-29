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

import assets.messages.ExtendContractMessages
import forms.sections.businessOnOwnAccount.ExtendContractFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.businessOnOwnAccount.ExtendContractView

class ExtendContractViewSpec extends YesNoViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.extendContract"

  val form = new ExtendContractFormProvider()()

  val view = injector.instanceOf[ExtendContractView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "ExtendContractView" when {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "the WhoAreYou is Worker" must {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ExtendContractMessages.Worker.title, Some(ExtendContractMessages.Worker.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(ExtendContractMessages.Worker.heading)
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ExtendContractMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe ExtendContractMessages.no
      }
    }

    "the WhoAreYou is Hirer" must {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ExtendContractMessages.Hirer.title, Some(ExtendContractMessages.Hirer.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(ExtendContractMessages.Hirer.heading)
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ExtendContractMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe ExtendContractMessages.no
      }
    }
  }
}
