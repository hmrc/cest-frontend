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

package connectors.mocks

import connectors.DecisionConnector
import models.{DecisionResponse, ErrorResponse, Interview}
import org.scalamock.scalatest.MockFactory
import play.api.libs.json.Writes
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.{ExecutionContext, Future}

trait MockDecisionConnector extends MockFactory {

  lazy val mockDecisionConnector = mock[DecisionConnector]

  def mockDecide(decisionRequest: Interview, writes: Writes[Interview] = Interview.writes)(response: Either[ErrorResponse, DecisionResponse]): Unit = {
    (mockDecisionConnector.decide(_: Interview, _: Writes[Interview])(_: HeaderCarrier, _: ExecutionContext))
      .expects(decisionRequest, *,  *, *)
      .returns(Future.successful(response))
  }
}