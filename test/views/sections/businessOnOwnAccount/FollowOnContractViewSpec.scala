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

import assets.messages.FollowOnContractMessages
import forms.sections.businessOnOwnAccount.FollowOnContractFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.mvc.Request
import play.twirl.api.HtmlFormat
import views.behaviours.YesNoViewBehavioursNew
import views.html.sections.businessOnOwnAccount.FollowOnContractView

class FollowOnContractViewSpec extends YesNoViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.followOnContract"

  val form = new FollowOnContractFormProvider()()

  val view: FollowOnContractView = injector.instanceOf[FollowOnContractView]

  def createView: () => HtmlFormat.Appendable = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm: Form[_] => HtmlFormat.Appendable = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest: Request[_] => HtmlFormat.Appendable = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "FollowOnContractView" when {

    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    behave like yesNoPage(createViewUsingForm, messageKeyPrefix)

    "the WhoAreYou is Worker" must {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(FollowOnContractMessages.Worker.title, Some(FollowOnContractMessages.Worker.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(FollowOnContractMessages.Worker.heading)
      }

      "have the correct p" in {
        document.select(Selectors.p(1)).text mustBe FollowOnContractMessages.Worker.p
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe FollowOnContractMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe FollowOnContractMessages.no
      }
    }

    "the WhoAreYou is Hirer" must {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(FollowOnContractMessages.Hirer.title, Some(FollowOnContractMessages.Hirer.subheading))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include(FollowOnContractMessages.Hirer.heading)
      }

      "have the correct p" in {
        document.select(Selectors.p(1)).text mustBe FollowOnContractMessages.Hirer.p
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe FollowOnContractMessages.yes
        document.select(Selectors.multichoice(2)).text mustBe FollowOnContractMessages.no
      }
    }
  }
}
