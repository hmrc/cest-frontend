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

import assets.messages.ContractStartedOptimisedMessages
import config.featureSwitch.FeatureSwitching
import forms.sections.setup.ContractStartedFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.setup.ContractStartedView

class ContractStartedViewSpec extends YesNoViewBehavioursNew with FeatureSwitching{

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.contractStarted"

  val form = new ContractStartedFormProvider()()(workerFakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[ContractStartedView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "ContractStarted view" must {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = false)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {

        document.title mustBe title(ContractStartedOptimisedMessages.Worker.title)
      }

      "have the correct heading" in {

        document.select(Selectors.heading).text must include(ContractStartedOptimisedMessages.Worker.heading)
      }

      "have the correct radio option messages" in {

        document.select(Selectors.multichoice(1)).text mustBe ContractStartedOptimisedMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe ContractStartedOptimisedMessages.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {

        document.title mustBe title(ContractStartedOptimisedMessages.Hirer.title)
      }

      "have the correct heading" in {

        document.select(Selectors.heading).text must include(ContractStartedOptimisedMessages.Hirer.heading)
      }

      "have the correct radio option messages" in {

        document.select(Selectors.multichoice(1)).text mustBe ContractStartedOptimisedMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe ContractStartedOptimisedMessages.no
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {

        document.title mustBe title(ContractStartedOptimisedMessages.NonTailored.title)
      }

      "have the correct heading" in {

        document.select(Selectors.heading).text must include(ContractStartedOptimisedMessages.NonTailored.heading)
      }

      "have the correct radio option messages" in {

        document.select(Selectors.multichoice(1)).text mustBe ContractStartedOptimisedMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe ContractStartedOptimisedMessages.no
      }
    }
  }
}
