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

package assets.messages.results

object UndeterminedDecisionMessages extends BaseResultMessages {

  object Site {
    val contactDetails = "Telephone: 0300 123 2326 Email: ir35@hmrc.gov.uk"
  }

  object WorkerIR35 {
    val heading = "Unable to make a determination"
    val whyResult1 = "We cannot determine from your answers if the off-payroll working rules apply to this work."
    val whyResult2 = "We will need more information to check your employment status for tax."
    val doNextP1 = "Download a copy of this result and show it to your client. Check your answers with them to make sure they reflect your actual or expected working practices."
    val doNextP2 = "If you need more guidance, you should contact HMRC’s Employment Status and Intermediaries helpline."
    val doNextP3 = "You could also read Chapter 5 of the Employment Status Manual (opens in a new window)."
  }

  object HirerPAYE {
    val heading = "Unable to make a determination"
    val whyResult = "It is not clear from your answers if the worker is employed or self-employed for tax purposes for this work."
    val doNextP1_WorkerKnown = "Firstly, you should download a copy of this result and show it to the worker. Check your answers with them to make sure they reflect the actual or expected working practices. If the worker gives you any new information, you should use this tool again."
    val doNextP1_WorkerNotKnown = "Once your organisation knows who the worker is, you may get more information about their working practices. Then you can use this tool again to check if this information will change your determination."
    val doNextP2 = "You could also read guidance in the Employment Status Manual (opens in a new window). This will provide you with more information about determining the employment status of a worker for tax purposes."
    val doNextP3 = "If you need more help on how to answer the questions within the tool, you can contact HMRC’s Employment Status and Intermediaries helpline."
  }

  object Agent {
    val heading = "Unable to make a determination"
    val whyResult_p1 = "We cannot determine from your answers if the off-payroll working rules apply to this work."
    val whyResult_p2 = "We will need more information to check your employment status for tax."
    val doNext_p1 = "If this result is different from the one you are checking, download a copy of this result and show it to your worker’s client. You should check your answers with them to make sure they are correct."
    val doNext_p2 = "If you need more guidance, you could also read Chapter 5 of the Employment Status Manual (opens in a new window)."
  }

  object HirerIR35 {
    val heading = "Unable to make a determination"
    val whyResult1 = "We cannot determine from your answers if the off-payroll working rules apply to this work."
    val whyResult2 = "We will need more information to check your employment status for tax."
    val doNextP1_WorkerKnown = "You should download a copy of this result to show it to the worker. Check your answers with them to make sure they reflect the actual or expected working practices."
    val doNextP1_WorkerNotKnown = "Once your organisation knows who the worker is, you may get more information about their working practices. Then you can use this tool again to check if this information will change your determination."
    val doNextP2 = "If you need more guidance, contact HMRC’s Employment Status and Intermediaries helpline."
    val doNextP3 = "You could also read Chapter 5 of the Employment Status Manual (opens in a new window)."

  }

  object WorkerPAYE {
    val heading = "Unable to make a determination"
    val whyResult = "It is not clear from your answers if you are employed or self-employed for tax purposes for this work."
    val doNextP1 = "Firstly, you should download a copy of this result and show it to your client. Check your answers with them to make sure they reflect the actual or expected working practices."
    val doNextP2 = "You could also read guidance in the Employment Status Manual (opens in a new window). This will provide you with more information about determining your employment status for tax purposes."
    val doNextP3 = "If you need more help on how to answer the questions within the tool, you can contact HMRC’s Employment Status and Intermediaries helpline."
  }
}
