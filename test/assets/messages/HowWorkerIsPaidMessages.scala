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

  val subheading = "About the worker’s financial risk"

  object WorkerOptimised {
    val heading = "On what basis will you be compensated for this work?"
    val title = heading
    val salary = "By an hourly, daily or weekly rate."
    val fixed = "By a fixed price for a specific project."
    val proRata = "By the amount of work completed."
    val commision = "By a percentage of the sales you generate."
    val profits = "By a percentage of your client’s profits or savings."
  }

  object HirerOptimised {
    val heading = "On what basis will the worker be compensated for this work?"
    val title = heading
    val salary = "By an hourly, daily or weekly rate."
    val fixed = "By a fixed price for a specific project."
    val proRata = "By the amount of work completed."
    val commision = "By a percentage of the sales the worker generates."
    val profits = "By a percentage of your organisation’s profits or savings."
  }

  object Worker {
    val heading = "What is the main way you are paid for this engagement?"
    val title = heading
    val salary = "An hourly, daily or weekly rate"
    val fixed = "A fixed price for a specific piece of work"
    val proRata = "An amount based on how much work is completed"
    val commision = "A percentage of the sales you make"
    val profits = "A percentage of the end client’s profits or savings"
  }

  object Hirer {
    val heading = "What is the main way the worker is paid for this engagement?"
    val title = heading
    val salary = "An hourly, daily or weekly rate"
    val fixed = "A fixed price for a specific piece of work"
    val proRata = "An amount based on how much work is completed"
    val commision = "A percentage of the sales the worker makes"
    val profits = "A percentage of your profits or savings"
  }

  object NonTailored {
    val heading = "What is the main way the worker is paid for this engagement?"
    val title = heading
    val salary = "An hourly, daily or weekly rate"
    val fixed = "A fixed price for a specific piece of work"
    val proRata = "An amount based on how much work is completed"
    val commision = "A percentage of the sales the worker makes"
    val profits = "A percentage of the end client’s profits or savings"
  }

}
