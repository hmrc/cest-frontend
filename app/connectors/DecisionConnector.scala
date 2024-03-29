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

package connectors

import config.featureSwitch.FeatureSwitching
import connectors.httpParsers.DecisionHttpParser.DecisionReads
import javax.inject.Inject
import models._
import play.api.Logging
import play.api.libs.json.{Json, Writes}
import play.mvc.Http.Status._
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.http.HttpClient

import scala.concurrent.{ExecutionContext, Future}

class DecisionConnector @Inject()(httpClient: HttpClient,
                                  servicesConfig: ServicesConfig,
                                  timestamp: Timestamp) extends FeatureSwitching with Logging {

  lazy val baseUrl: String = servicesConfig.baseUrl("cest-decision")
  lazy val decideUrl = s"$baseUrl/cest-decision/decide"

  private[connectors] val handleUnexpectedError: PartialFunction[Throwable, Left[ErrorResponse, Nothing]] = {
    case ex: Exception => logger.error("[DecisionConnector][handleUnexpectedError]", ex)
      Left(ErrorResponse(INTERNAL_SERVER_ERROR, s"HTTP exception returned from decision API: ${ex.getMessage}"))
  }

  def decide(decisionRequest: Interview, writer: Writes[Interview] = Interview.writes)
               (implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Either[ErrorResponse, DecisionResponse]] = {
    logger.debug(s"[DecisionConnector][decide] ${Json.toJson(decisionRequest)(writer)}")
    httpClient.POST(s"$decideUrl", decisionRequest)(writer, DecisionReads, hc, ec) recover handleUnexpectedError
  }
}
