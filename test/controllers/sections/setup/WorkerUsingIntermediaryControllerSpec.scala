/*
 * Copyright 2019 HM Revenue & Customs
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
import forms.sections.setup.{WorkerTypeFormProvider, WorkerUsingIntermediaryFormProvider}
import models.Answers._
import models._
import models.requests.DataRequest
import models.sections.setup.WorkerType
import navigation.mocks.FakeNavigators.FakeSetupNavigator
import pages.sections.setup.{WorkerTypePage, WorkerUsingIntermediaryPage}
import play.api.data.Form
import play.api.libs.json.Json
import play.api.test.Helpers._
import uk.gov.hmrc.http.cache.client.CacheMap
import views.html.sections.setup.WorkerUsingIntermediaryView

class WorkerUsingIntermediaryControllerSpec extends ControllerSpecBase {

  val formProvider = new WorkerUsingIntermediaryFormProvider()
  val form = formProvider()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[WorkerUsingIntermediaryView]

  def controller(dataRetrievalAction: DataRetrievalAction = FakeEmptyCacheMapDataRetrievalAction) =
    new WorkerUsingIntermediaryController(
      FakeIdentifierAction,
      dataRetrievalAction,
      new DataRequiredActionImpl(messagesControllerComponents),
      formProvider,
      messagesControllerComponents,
      view,
      compareAnswerService = mockCompareAnswerService,
      dataCacheConnector = mockDataCacheConnector,
      navigator = FakeSetupNavigator,
      frontendAppConfig
    )

  def viewAsStringInt(form: Form[_] = form) = view(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

  val validData = Map(WorkerTypePage.toString -> Json.toJson(Answers(WorkerType.values.head,0)))
  val validDataInt = Map(WorkerUsingIntermediaryPage.toString -> Json.toJson(Answers(true, 0)))

  "WorkerType Controller" must {

    "return OK and the correct view for a GET in the optimised view" in {

      val result = controller().onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe OK
      contentAsString(result) mustBe viewAsStringInt()
    }

    "populate the view correctly on a GET when the question has previously been answered for the optimised flow" in {


      val getRelevantData = FakeGeneralDataRetrievalAction(Some(CacheMap(cacheMapId, validDataInt)))

      val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

      contentAsString(result) mustBe viewAsStringInt(form.fill(true))
    }

    "redirect to the next page when valid data is submitted for the optimised flow" in {


      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "true"))
      val answers = userAnswers.set(WorkerTypePage,0,WorkerType.LimitedCompany)
      mockOptimisedConstructAnswers(DataRequest(postRequest,"id",answers),WorkerType)(answers)

      mockSave(CacheMap(cacheMapId, validData))(CacheMap(cacheMapId, validData))

      val result = controller().onSubmit(NormalMode)(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted for the optimised flow" in {


      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
      val boundForm = form.bind(Map("value" -> "invalid value"))

      val result = controller().onSubmit(NormalMode)(postRequest)

      status(result) mustBe BAD_REQUEST
      contentAsString(result) mustBe viewAsStringInt(boundForm)
    }

    "redirect to Index Controller for a GET if no existing data is found" in {
      val result = controller(FakeDontGetDataDataRetrievalAction).onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
    }

    "redirect to Index Controller for a POST if no existing data is found" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", WorkerType.options.head.value))
      val result = controller(FakeDontGetDataDataRetrievalAction).onSubmit(NormalMode)(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
    }
  }
}