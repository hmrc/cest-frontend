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

package forms

import base.GuiceAppSpecBase

import forms.behaviours.BooleanFieldBehaviours
import forms.sections.personalService.RejectSubstituteFormProvider
import play.api.data.FormError

class RejectSubstituteFormProviderSpec extends BooleanFieldBehaviours with GuiceAppSpecBase {

  val requiredKey = "rejectSubstitute.error.required"
  val invalidKey = "error.required"

  val form = new RejectSubstituteFormProvider()()(fakeDataRequest, frontendAppConfig)
  val fieldName = "value"

  ".value" must {

    behave like booleanField(
      form,
      fieldName,
      invalidError = FormError(fieldName, invalidKey)
    )

    "for the optimised flow" should {

      "if the user type is 'Worker'" must {


        val form = new RejectSubstituteFormProvider()()(workerFakeDataRequest, frontendAppConfig)

        behave like mandatoryField(
          form,
          fieldName,
          requiredError = FormError(fieldName, s"worker.optimised.$requiredKey")
        )
      }

      "if the user type is 'Hirer'" must {


        val form = new RejectSubstituteFormProvider()()(hirerFakeDataRequest, frontendAppConfig)

        behave like mandatoryField(
          form,
          fieldName,
          requiredError = FormError(fieldName, s"hirer.optimised.$requiredKey")
        )
      }
    }
  }
}
