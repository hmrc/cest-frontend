/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.offpayroll.connectors

import com.google.inject.ImplementedBy
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.Request
import uk.gov.hmrc.offpayroll.FrontendPlatformAnalyticsConnector
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpPost}
import uk.gov.hmrc.offpayroll.modelsFormat._

import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[FrontendPlatformAnalyticsConnector])
trait PlatformAnalyticsConnector {

  implicit val dimensionWrites = Json.writes[DimensionValue]
  implicit val eventWrites = Json.writes[Event]
  implicit val analyticsWrites = Json.writes[AnalyticsRequest]

  val serviceURL: String
  val http: HttpPost

  def sendEvent(request: AnalyticsRequest)(implicit hc:HeaderCarrier): Unit =  {
    http.POST(serviceURL, request).map(_ => ()).recover {
      case e: Exception => Logger.error(s"Couldn't send analytics event $request", e)
    }
  }

}

case class DimensionValue(index: Int, value: String)
case class Event(category: String, action: String, label: String, dimensions: Seq[DimensionValue])
case class AnalyticsRequest(gaClientId: String, events: Seq[Event])