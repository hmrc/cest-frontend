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

object ArrangedSubstituteMessages extends BaseMessages {

  val subheading = "About substitutes and helpers"

  object Worker {
    val heading = "Has your business arranged for someone else (a substitute) to do the work instead of you during this engagement?"
    val title = heading
    val p1 = "This means someone who:"
    val b1 = "Was equally skilled, qualified, security cleared and able to perform your duties"
    val b2 = "Was not interviewed by the end client before they started (except for any verification checks)"
    val b3 = "Was not from a pool or bank of workers regularly engaged by the end client"
    val b4 = "Did all of your tasks for that period of time"
    val b5 = "Was substituted because you were unwilling but not unable to do the work"
    val yesClientAgreed = "Yes - and the end client agreed"
    val yesClientNotAgreed = "Yes - but the end client did not agree"
    val no = "No - it has not happened"
  }

  object Hirer {
    val heading = "Has the worker’s business arranged for someone else (a substitute) to do the work instead of them during this engagement?"
    val title = heading
    val p1 = "This means someone who:"
    val b1 = "Was equally skilled, qualified, security cleared and able to perform the worker’s duties"
    val b2 = "Was not interviewed by you before they started (except for any verification checks)"
    val b3 = "Was not from a pool or bank of workers regularly engaged by you"
    val b4 = "Did all of the worker’s tasks for that period of time"
    val b5 = "Was substituted because the worker was unwilling but not unable to do the work"
    val yesClientAgreed = "Yes - and we agreed"
    val yesClientNotAgreed = "Yes - but we did not agree"
    val no = "No - it has not happened"
  }

  object NonTailored {
    val heading = "Has the worker’s business arranged for someone else (a substitute) to do the work instead of them during this engagement?"
    val title = heading
    val p1 = "This means someone who:"
    val b1 = "was equally skilled, qualified, security cleared and able to perform the worker’s duties"
    val b2 = "was not interviewed by the end client before they started (except for any verification checks)"
    val b3 = "was not from a pool or bank of workers regularly engaged by the end client"
    val b4 = "did all of the worker’s tasks for that period of time"
    val b5 = "was substituted because the worker was unwilling but not unable to do the work"
    val yesClientAgreed = "Yes - and the client agreed"
    val yesClientNotAgreed = "Yes - but the client did not agree"
    val no = "No - it has not happened"
  }

}