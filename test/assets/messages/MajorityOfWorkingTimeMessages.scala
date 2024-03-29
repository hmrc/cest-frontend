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

package assets.messages

object MajorityOfWorkingTimeMessages extends BaseMessages {

  object Worker {
    val error = "Select yes if the work will take up the majority of your available working time"
    val title = "Will this work take up the majority of your available working time?"
    val heading = "Will this work take up the majority of your available working time?"
    val subheading = "Worker’s contracts"
    val p1 = "This includes preparation or any other time necessary to deliver the work, even if it is not referred to in the contract."
    val p2 = "Read more about the worker’s available working time (opens in new tab)."

  }

  object Hirer {
    val error = "Select yes if the work will take up the majority of the worker’s available working time"
    val title = "Will this work take up the majority of the worker’s available working time?"
    val heading = "Will this work take up the majority of the worker’s available working time?"
    val subheading = "Worker’s contracts"
    val p1 = "This includes preparation or any other time necessary to deliver the work, even if it is not referred to in the contract."
    val p2 = "Read more about the worker’s available working time (opens in new tab)."
  }
}
