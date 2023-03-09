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

package views.errors

import messages.AllowCookiesMessages
import play.api.routing.Router.empty.routes
import views.behaviours.ViewBehavioursNew
import views.html.errors.AllowCookiesView

class AllowCookiesViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors {
    val startAgainButton = "submit"
  }

  val view = injector.instanceOf[AllowCookiesView]

  def createView = () => view(frontendAppConfig, frontendAppConfig.govUkStartPageUrl, Some("lang"))(fakeRequest, messages)

  "Allow Cookies view" must {
    behave like normalPage(createView, "allow.cookies", hasSubheading = false)
  }

  "Have a link to the IndexController" in {
    val button = asDocument(createView()).getElementById(Selectors.startAgainButton)
    button.attr("href") must include (controllers.routes.StartAgainController.redirectToDisclaimer.url)

    button.text mustBe AllowCookiesMessages.startAgain
  }
}
