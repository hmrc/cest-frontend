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

package controllers.sections.financialRisk

import config.featureSwitch.OptimisedFlow
import akka.util.ByteString
import connectors.FakeDataCacheConnector
import connectors.mocks.MockDataCacheConnector
import controllers.ControllerSpecBase
import controllers.actions._
import forms.HowWorkerIsPaidFormProvider
import models.Answers._
import models.CannotClaimAsExpense.WorkerProvidedMaterials
import models.PutRightAtOwnCost.OutsideOfHoursNoCharge
import models.requests.DataRequest
import models._
import navigation.FakeNavigator
import org.mockito.Matchers
import org.mockito.Mockito.when
import pages.sections.financialRisk.{CannotClaimAsExpensePage, HowWorkerIsPaidPage, PutRightAtOwnCostPage}
import pages.sections.personalService.DidPaySubstitutePage
import play.api.data.Form
import play.api.http.HttpEntity
import play.api.libs.json.Json
import play.api.mvc.{Call, ResponseHeader, Result}
import play.api.test.Helpers._
import services.mocks.MockCompareAnswerService
import uk.gov.hmrc.http.cache.client.CacheMap
import views.html.sections.financialRisk.HowWorkerIsPaidView
import views.html.subOptimised.sections.financialRisk.{HowWorkerIsPaidView => SubOptimisedHowWorkerIsPaidView}

import scala.concurrent.Future
class HowWorkerIsPaidControllerSpec extends ControllerSpecBase {

  val formProvider = new HowWorkerIsPaidFormProvider()
  val form = formProvider()

  val subOptimisedView = injector.instanceOf[SubOptimisedHowWorkerIsPaidView]
  val optimisedView = injector.instanceOf[HowWorkerIsPaidView]

  def controller(dataRetrievalAction: DataRetrievalAction = FakeEmptyCacheMapDataRetrievalAction) = new HowWorkerIsPaidController(
    FakeIdentifierAction,
    dataRetrievalAction,
    new DataRequiredActionImpl(messagesControllerComponents),
    formProvider,
    controllerComponents = messagesControllerComponents,
    subOptimisedView = subOptimisedView,
    optimisedView = optimisedView,
    checkYourAnswersService = mockCheckYourAnswersService,
    compareAnswerService = mockCompareAnswerService,
    dataCacheConnector = mockDataCacheConnector,
    decisionService = mockDecisionService,
    navigator = fakeNavigator,
    frontendAppConfig
  )

  val validData = Map(HowWorkerIsPaidPage.toString -> Json.toJson(Answers(HowWorkerIsPaid.values.head,0)))

  "HowWorkerIsPaid Controller" when {

    "the optimised flow is enabled" must {

      def viewAsString(form: Form[_] = form) = optimisedView(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

      "return OK and the correct view for a GET" in {

        enable(OptimisedFlow)
        val result = controller().onPageLoad(NormalMode)(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe viewAsString()
      }

      "populate the view correctly on a GET when the question has previously been answered" in {
        enable(OptimisedFlow)
        val getRelevantData = new FakeGeneralDataRetrievalAction(Some(CacheMap(cacheMapId, validData)))

        val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

        contentAsString(result) mustBe viewAsString(form.fill(HowWorkerIsPaid.values.head))
      }

      "redirect to the next page when valid data is submitted" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", HowWorkerIsPaid.options.head.value))
        val answers = userAnswers.set(HowWorkerIsPaidPage,0,HowWorkerIsPaid.HourlyDailyOrWeekly)
        mockConstructAnswers(DataRequest(postRequest,"id",answers),HowWorkerIsPaid)(answers)

        mockSave(CacheMap(cacheMapId, validData))(CacheMap(cacheMapId, validData))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(onwardRoute.url)
      }

      "return a Bad Request and errors when invalid data is submitted" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
        val boundForm = form.bind(Map("value" -> "invalid value"))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe BAD_REQUEST
        contentAsString(result) mustBe viewAsString(boundForm)
      }

      "redirect to Index Controller for a GET if no existing data is found" in {
        enable(OptimisedFlow)
        val result = controller(FakeDontGetDataDataRetrievalAction).onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }

      "redirect to Index Controller for a POST if no existing data is found" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", HowWorkerIsPaid.options.head.value))
        val result = controller(FakeDontGetDataDataRetrievalAction).onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }
    }

    "the optimised flow is disabled" must {

      def viewAsString(form: Form[_] = form) = subOptimisedView(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

      "return OK and the correct view for a GET" in {

        val result = controller().onPageLoad(NormalMode)(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe viewAsString()
      }

      "populate the view correctly on a GET when the question has previously been answered" in {
        val getRelevantData = new FakeGeneralDataRetrievalAction(Some(CacheMap(cacheMapId, validData)))

        val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

        contentAsString(result) mustBe viewAsString(form.fill(HowWorkerIsPaid.values.head))
      }

      "redirect to the next page when valid data is submitted" in {
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", HowWorkerIsPaid.options.head.value))

        val answers = userAnswers.set(HowWorkerIsPaidPage,0,HowWorkerIsPaid.HourlyDailyOrWeekly)
        mockConstructAnswers(DataRequest(postRequest,"id",answers),HowWorkerIsPaid)(answers)
        mockSave(CacheMap(cacheMapId, validData))(CacheMap(cacheMapId, validData))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER

      }

      "return a Bad Request and errors when invalid data is submitted" in {
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
        val boundForm = form.bind(Map("value" -> "invalid value"))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe BAD_REQUEST
        contentAsString(result) mustBe viewAsString(boundForm)
      }

      "redirect to Index Controller for a GET if no existing data is found" in {
        val result = controller(FakeDontGetDataDataRetrievalAction).onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }

      "redirect to Index Controller for a POST if no existing data is found" in {
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", HowWorkerIsPaid.options.head.value))
        val result = controller(FakeDontGetDataDataRetrievalAction).onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }
    }
  }
}
