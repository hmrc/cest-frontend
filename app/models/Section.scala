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

package models

import play.api.mvc.QueryStringBindable

object Section extends Enumeration {
  type SectionEnum = Value
  val setup = Value("setup")
  val earlyExit = Value("earlyExit")
  val control = Value("control")
  val personalService = Value("personalService")
  val financialRisk = Value("financialRisk")
  val partAndParcel = Value("partAndParcel")
  val businessOnOwnAccount = Value("businessOnOwnAccount")

  implicit object SectionBinder extends QueryStringBindable.Parsing[Value](
    withName, _.toString, (k: String, e: Exception) => "Cannot parse %s as MyEnum: %s".format(k, e.getMessage)
  )
}
