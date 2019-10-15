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

package assets.messages

object ScheduleOfWorkingHoursMessages extends BaseMessages {

  object Worker {
    val error = "Select yes if your client will decide the working hours"
    val heading = "Will your client decide the working hours?"
    val title = heading
    val yesClientDecides = "Yes"
    val noWorkerDecides = "No, you solely decide"
    val partly = "No, you and your client agree"
    val notApplicable = "No, the work is based on agreed deadlines"
  }

  object Hirer {
    val error = "Select yes if your organisation will decide the working hours"
    val heading = "Will your organisation decide the working hours?"
    val title = heading
    val yesClientDecides = "Yes"
    val noWorkerDecides = "No, the worker solely decides"
    val partly = "No, your organisation and the worker agree"
    val notApplicable = "No, the work is based on agreed deadlines"
  }
}
