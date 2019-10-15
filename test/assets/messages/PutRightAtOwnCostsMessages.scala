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

object PutRightAtOwnCostsMessages extends BaseMessages {

  object Worker {
    val error = "Select if you would have to put your work right if your client was not happy with it"
    val title = "If the client was not happy with your work, would you have to put it right?"
    val heading = "If the client was not happy with your work, would you have to put it right?"
    val yesAdditionalCost = "Yes, unpaid and you would have extra costs that your client would not pay for"
    val yesAdditionalCharge = "Yes, unpaid but your only cost would be losing the opportunity to do other work"
    val noUsualHours = "Yes, you would fix it in your usual hours at your usual rate or fee"
    val noSingleEvent = "No, the work is time-specific or for a single event"
    val no = "No"
  }

  object Hirer {
    val error = "Select if the worker would have to put the work right if your organisation was not happy with it"
    val title = "If your organisation was not happy with the work, would the worker have to put it right?"
    val heading = "If your organisation was not happy with the work, would the worker have to put it right?"
    val yesAdditionalCost = "Yes, unpaid and they would have extra costs that your organisation would not pay for"
    val yesAdditionalCharge = "Yes, unpaid but their only cost would be losing the opportunity to do other work"
    val noUsualHours = "Yes, they would fix it in their usual hours at their usual rate or fee"
    val noSingleEvent = "No, the work is time-specific or for a single event"
    val no = "No"
  }
}
