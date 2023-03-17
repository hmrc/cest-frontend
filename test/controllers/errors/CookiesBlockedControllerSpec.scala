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

package controllers.errors

import connectors.DataCacheConnector
import controllers.ControllerSpecBase
import controllers.actions.IdentifierAction
import play.api.i18n.{Lang, Messages}
import play.api.test.Helpers._
import views.html.errors.CookiesBlockedView

class CookiesBlockedControllerSpec extends ControllerSpecBase {

  val identify = injector.instanceOf[IdentifierAction]
  val dataCacheConnector = injector.instanceOf[DataCacheConnector]
  val cookiesView = injector.instanceOf[CookiesBlockedView]

  val controller = new CookiesBlockedController(
    frontendAppConfig,
    identify,
    messagesControllerComponents,
    cookiesView)

  "CookiesBlockedController.onPageLoad" must {

    lazy val result = controller.onPageLoad()(fakeRequest)

    "return 200 for a GET" in {
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      contentAsString(result) mustBe cookiesView(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, None)(fakeRequest, messages).toString
    }
  }

  "CookiesBlockedController.onPageLoad(Some(en))" must {

    lazy val result = controller.onPageLoad(Some("en"))(fakeRequest)

    "return 200 for a GET" in {
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      contentAsString(result) mustBe cookiesView(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, Some("en"))(fakeRequest, messages).toString
    }
  }

  "CookiesBlockedController.onPageLoad(Some(cy))" must {

    lazy val result = controller.onPageLoad(Some("cy"))(fakeRequest)

    val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

    "return 200 for a GET" in {
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      contentAsString(result) mustBe cookiesView(frontendAppConfig, controllers.routes.StartAgainController.redirectToDisclaimer.url, Some("cy"))(fakeRequest, welshMessages).toString
    }
  }

  "CookiesBlockedController.onSubmit" must {

    lazy val result = controller.onSubmit()(fakeRequest)

    "return 303 for a GET" in {
      status(result) mustBe SEE_OTHER
    }

    "redirect to the correct url" in {
      redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)

    }
  }
}
