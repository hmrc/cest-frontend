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

import assets.messages.{BenefitsMessages, SubHeadingMessages}
import forms.sections.partAndParcel.BenefitsFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.partParcel.BenefitsView

class BenefitsViewSpec extends YesNoViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.benefits"

  val form = new BenefitsFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[BenefitsView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages,frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages,frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "Benefits view" must {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(BenefitsMessages.Worker.title, Some(SubHeadingMessages.partAndParcel))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(BenefitsMessages.Worker.heading)
      }

      "have the correct bullet points" in {
        document.select(Selectors.p(1)).text must include(BenefitsMessages.Worker.p1)
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title must include(title(BenefitsMessages.Hirer.title, Some(SubHeadingMessages.partAndParcel)))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(BenefitsMessages.Hirer.heading)
      }

      "have the correct bullet points" in {
        document.select(Selectors.p(1)).text must include(BenefitsMessages.Hirer.p1)
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {
        document.title mustBe title(BenefitsMessages.Worker.title, Some(SubHeadingMessages.partAndParcel))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(BenefitsMessages.Worker.heading)
      }

      "have the correct bullet points" in {
        document.select(Selectors.p(1)).text must include(BenefitsMessages.Worker.p1)
      }
    }
  }
}
