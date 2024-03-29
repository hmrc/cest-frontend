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

object FollowOnContractMessages extends BaseMessages {

  val error = "Select yes if this contract will start immediately after the previous one ended"

  object Worker {
    val title = "Will this contract start immediately after the previous one ended?"
    val heading = "Will this contract start immediately after the previous one ended?"
    val subheading = "Worker’s contracts"
    val p = "This does not include any holiday period between the two contracts."
  }

  object Hirer {
    val title = "Will this contract start immediately after the previous one ended?"
    val heading = "Will this contract start immediately after the previous one ended?"
    val subheading = "Worker’s contracts"
    val p = "This does not include any holiday period between the two contracts."
  }
}
