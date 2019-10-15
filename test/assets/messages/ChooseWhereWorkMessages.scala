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

object ChooseWhereWorkMessages extends BaseMessages {

  object Worker {
    val error = "Select yes if your client will decide where you do the work"
    val heading = "Will your client decide where you do the work?"
    val title = heading
    val clientDecides = "Yes"
    val workerDecides = "No, you solely decide"
    val noTaskDeterminate = "No, the task sets the location"
    val partly = "No, some work has to be done in an agreed location and some can be your choice"
  }

  object Hirer {
    val error = "Select yes if your organisation will decide where the worker does the work"
    val heading = "Will your organisation decide where the worker does the work?"
    val title = heading
    val clientDecides = "Yes"
    val workerDecides = "No, the worker decides"
    val noTaskDeterminate = "No, the task sets the location"
    val partly = "No, some work has to be done in an agreed location and some can be the worker’s choice"
  }
}
