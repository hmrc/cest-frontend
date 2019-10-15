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

package views

import controllers.routes
import forms.CustomisePDFFormProvider
import models.{AdditionalPdfDetails, NormalMode}
import play.api.data.Form
import views.behaviours.QuestionViewBehaviours
import views.html.CustomisePDFView

class CustomisePDFViewSpec extends QuestionViewBehaviours[AdditionalPdfDetails] {

  val messageKeyPrefix = "customisePDF"

  val form = new CustomisePDFFormProvider()()

  val view = injector.instanceOf[CustomisePDFView]

  def createView = () => view(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[AdditionalPdfDetails]) => view(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  "CustomisePDF view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = false)

    behave like pageWithBackLink(createView)

    behave like pageWithTextFields(
      createViewUsingForm,
      messageKeyPrefix,
      routes.PDFController.onSubmit(NormalMode).url,
      "completedBy", "client", "job", "reference"
    )
  }
}
