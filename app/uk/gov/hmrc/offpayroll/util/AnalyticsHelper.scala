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

package uk.gov.hmrc.offpayroll.util

import uk.gov.hmrc.offpayroll.connectors.{AnalyticsRequest, DimensionValue, Event}

/**
  * Created by work on 13/04/2017.
  */
object AnalyticsHelper {

  //FIXME these values are to be decided
  private val CATEGORY = "opf_logging"
  private val ACTION = "logging_begin"
  private val LABEL = "begin"
  private val GA_CLIENT_ID = "GA1.1.283183975.1456746121"

  def buildAnalyticsRequest(route: String, version: String, decision: String, interview: List[(String, List[String])]): AnalyticsRequest = {
    var index = 0

    def indexPlus1() : Int = {
      index = index + 1
      index
    }

    var dimensionValues: List[DimensionValue] = List()
    dimensionValues = dimensionValues :+ DimensionValue(indexPlus1,"version: " +  version)
    dimensionValues = dimensionValues :+ DimensionValue(indexPlus1,"route: " + route)
    dimensionValues = dimensionValues :+ DimensionValue(indexPlus1,"decision: " +  decision)

    interview.foreach(questionAndAnswer =>
      dimensionValues = dimensionValues :+ DimensionValue(indexPlus1,questionAndAnswer._1 + ": " + questionAndAnswer._2.mkString)
    )

    val event = Event(CATEGORY, ACTION, LABEL, dimensionValues)
    AnalyticsRequest(GA_CLIENT_ID, List(event))

  }


}
