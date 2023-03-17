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
import connectors.DataCacheConnector
import connectors.mocks.MockDataCacheConnector
import models.requests.{DataRequest, IdentifierRequest, OptionalDataRequest}
import org.scalatest.concurrent.ScalaFutures
import play.api.mvc.Result
import uk.gov.hmrc.http.cache.client.CacheMap

import scala.concurrent.Future

class DataRequiredActionSpec extends GuiceAppSpecBase with MockDataCacheConnector with ScalaFutures {

  class Harness extends DataRequiredActionImpl(messagesControllerComponents) {
    def callRefine[A](request: OptionalDataRequest[A]): Future[Either[Result, DataRequest[A]]] = refine(request)
  }


  "Data Required Action" when {
    "cookies are blocked and the flag is not defined" must {
      "redirect to the next page and define the flag" in {

        val action = new Harness

        val futureResult = action.callRefine(new OptionalDataRequest(fakeRequest, "id", None))

        whenReady(futureResult) { result =>
          result.userAnswers.isEmpty mustBe true
        }
      }
    }

    "cookies are blocked and the flag is defined" must {
      "redirect to the blocked cookies view" in {
        mockFetch("id")(Some(CacheMap("id", Map())))
        val action = new Harness(mockDataCacheConnector)

        val futureResult = action.callTransform(new IdentifierRequest(fakeRequest, "id"))

        whenReady(futureResult) { result =>
          result.userAnswers.isDefined mustBe true
        }
      }
    }
  }




}
