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

package controllers.actions

import base.GuiceAppSpecBase
import models.requests.{DataRequest, OptionalDataRequest}
import org.scalatest.concurrent.ScalaFutures
import play.api.mvc.Result
import play.api.mvc.Results.Redirect

import scala.concurrent.Future

class DataRequiredActionSpec extends GuiceAppSpecBase with ScalaFutures {

  object Harness extends DataRequiredActionImpl(messagesControllerComponents) {
    def callRefine[A](request: OptionalDataRequest[A]): Future[Either[Result, DataRequest[A]]] = refine(request)
    def callLanguageChanger(lang: Option[String]): Option[String] = languageChanger(lang)
    val callLang =
    val callCookiesBlocked =
  }


  "Data Required Action" when {
    "cookies are blocked and the flag is not defined" must {
      "redirect to the next page and define the flag" in {

        val futureResult = Harness.callRefine(new OptionalDataRequest(fakeRequest, "id", None))
        val expectedResult = Left(Redirect(controllers.routes.IndexController.onPageLoad(Some("1"))))

        whenReady(futureResult) { actualResult =>
          actualResult mustBe expectedResult
        }
      }
    }

    "cookies are blocked and the flag is defined" must {
      "redirect to the blocked cookies view" in {

        val futureResult = Harness.callRefine(new OptionalDataRequest(fakeRequest, "id", None))
        val expectedResult = Left(Redirect(controllers.routes.CookiesBlockedController.onPageLoad()))

        whenReady(futureResult) { actualResult =>
          actualResult mustBe expectedResult
        }
      }
    }
  }




}
