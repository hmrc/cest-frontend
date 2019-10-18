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

object HowWorkerIsPaidMessages extends BaseMessages {

  object Worker {
    val error = "Select how you will be paid for this work"
    val heading = "How will you be paid for this work?"
    val title = heading
    val salary = "An hourly, daily or weekly rate"
    val fixed = "A fixed price for the project"
    val proRata = "A fixed amount for each piece of work completed"
    val commision = "A percentage of the sales you generate"
    val profits = "A percentage of your client’s profits or savings"
  }

  object Hirer {
    val error = "Select how the worker will be paid for this work"
    val heading = "How will the worker be paid for this work?"
    val title = heading
    val salary = "An hourly, daily or weekly rate"
    val fixed = "A fixed price for the project"
    val proRata = "A fixed amount for each piece of work completed"
    val commision = "A percentage of the sales the worker generates"
    val profits = "A percentage of your organisation’s profits or savings"
  }
}
