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

object MultipleContractsMessages extends BaseMessages {

  object Hirer {
    val error = "Select yes if this contract stops the worker from doing similar work for other organisations"
    val title = "Does this contract stop the worker from doing similar work for other organisations?"
    val heading = title
    val subheading = "Worker’s contracts"
    val p = "This includes working for your competitors."
  }

  object Worker {
    val error = "Select yes if this contract stops you from doing similar work for other clients"
    val title = "Does this contract stop you from doing similar work for other clients?"
    val heading = title
    val subheading = "Worker’s contracts"
    val p = "This includes working for your client’s competitors."
  }
}
