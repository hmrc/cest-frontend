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

package controllers.sections.setup

import controllers.ControllerSpecBase
import controllers.actions._
import navigation.mocks.FakeNavigators.FakeSetupNavigator
import play.api.test.Helpers._
import views.html.sections.setup.AboutYourResultView
import play.api.test.FakeRequest

class AboutYourResultControllerSpec extends ControllerSpecBase {

  val view = injector.instanceOf[AboutYourResultView]
  val requestWithCookiesUrlParam = FakeRequest("GET", routes.AboutYourResultController.onPageLoad(Some("1")).url)

  def controller(dataRetrievalAction: DataRetrievalAction = FakeEmptyCacheMapDataRetrievalAction) = new AboutYourResultController(
    identify = FakeIdentifierAction,
    getData = dataRetrievalAction,
    requireData = new DataRequiredActionImpl(messagesControllerComponents),
    controllerComponents = messagesControllerComponents,
    view = view,
    appConfig = frontendAppConfig,
    checkYourAnswersService = mockCheckYourAnswersService,
    compareAnswerService = mockCompareAnswerService,
    dataCacheConnector = mockDataCacheConnector,
    navigator = FakeSetupNavigator
  )

  "AboutYou Controller" must {

    "return OK and the correct view for a GET" in {
      val result = controller().onPageLoad()(fakeRequest)
      status(result) mustBe OK
      contentAsString(result) mustBe view(routes.AboutYourResultController.onSubmit())(fakeRequest, messages, frontendAppConfig).toString
    }

    "redirect to the next page when valid data is submitted" in {
      val result = controller().onSubmit()(fakeRequest)
      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "redirect to Index Controller for a GET if no existing data is found" in {
      val result = controller(FakeDontGetDataDataRetrievalAction).onPageLoad()(fakeRequest)
      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad(Some("1")).url)
    }

    "redirect to Index Controller for a POST if no existing data is found" in {
      val result = controller(FakeDontGetDataDataRetrievalAction).onSubmit()(fakeRequest)
      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad(Some("1")).url)
    }

    "redirect to Cookies Blocked Controller for a GET if no existing data is found and the url param marker is found" in {
      val result = controller(FakeDontGetDataDataRetrievalAction).onPageLoad()(requestWithCookiesUrlParam)
      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(controllers.errors.routes.CookiesBlockedController.onPageLoad().url)
    }

    "return OK and the correct view for a GET if url param marker is found however there user answers are available" in {
      val result = controller().onPageLoad()(requestWithCookiesUrlParam)
      status(result) mustBe OK
      contentAsString(result) mustBe view(routes.AboutYourResultController.onSubmit())(requestWithCookiesUrlParam, messages, frontendAppConfig).toString
    }
  }
}
