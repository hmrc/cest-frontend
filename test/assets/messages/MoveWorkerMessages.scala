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

object MoveWorkerMessages extends BaseMessages {

  object Worker {
    val error = "Select yes if the task can be changed without your agreement"
    val heading = "Can the task be changed without your agreement?"
    val p1 = "This includes changing the project or base location."
    val title = heading
    val yesWithAgreement = "Yes"
    val yesWithoutAgreement = "No, you would have to agree"
    val no = "No, that would require a new contract or formal working arrangement"
  }

  object Hirer {
    val error = "Select yes if the task can be changed without the worker’s agreement"
    val heading = "Could the worker’s task be changed without their agreement?"
    val title = heading
    val p1 = "This includes changing the project or base location."
    val yesWithAgreement = "Yes"
    val yesWithoutAgreement = "No, they would have to agree"
    val no = "No, that would require a new contract or formal working arrangement"
  }
}
