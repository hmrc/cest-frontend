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
import play.api.i18n.{Lang, Messages, MessagesApi}
import play.api.routing.Router.empty.routes
import play.twirl.api.Html
import views.behaviours.ViewBehavioursNew
import views.html.errors.CookiesBlockedView

class CookiesBlockedViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors {
    val startAgainButton = "startAgain"
  }

  private trait Test {
    def messagesApi: MessagesApi = injector.instanceOf[MessagesApi]

}
  val view = injector.instanceOf[CookiesBlockedView]

  private trait EnTest extends Test {
    implicit def messages: Messages = messagesApi.preferred(Seq(Lang("en")))
  }

  private trait CyTest extends Test {
    implicit def messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
  }

  def createView = () => view(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, None)(fakeRequest, messages)

  "Allow Cookies view" must {
    behave like normalPage(createView, "cookies.blocked", hasSubheading = false)
  }

  "Have a link to the IndexController" in {
    val button = asDocument(createView()).getElementById(Selectors.startAgainButton)
    button.attr("href") must include (controllers.routes.StartAgainController.redirectToDisclaimer.url)

    button.text mustBe AllowCookiesMessages.startAgain
  }


  "Session blocked Page" must {

    "Display English by default" in new EnTest {

      val html: Html = view(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, None)
      val doc = asDocument(html)

      val heading = doc.getElementsByClass("govuk-heading-xl")
      heading.text mustBe "There has been a problem"

      val button = doc.getElementById(Selectors.startAgainButton)
      button.attr("href") mustBe controllers.routes.StartAgainController.redirectToDisclaimer.url
    }

    "Display English with url param lang switch" in new EnTest {

      val html: Html = view(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, Some("en"))
      val doc = asDocument(html)

      val heading = doc.getElementsByClass("govuk-heading-xl")
      heading.text mustBe "There has been a problem"

      val button = doc.getElementById(Selectors.startAgainButton)
      button.attr("href") mustBe (controllers.routes.StartAgainController.redirectToDisclaimer.url + "?lang=en")
    }

    "Display Welsh with url param lang switch" in new CyTest {

      val html: Html = view(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, Some("cy"))
      val doc = asDocument(html)

      val heading = doc.getElementsByClass("govuk-heading-xl")
      heading.text mustBe "Mae problem wedi codi"

      val button = doc.getElementById(Selectors.startAgainButton)

      button.attr("href") mustBe (controllers.routes.StartAgainController.redirectToDisclaimer.url + "?lang=cy")
    }

  }


}
