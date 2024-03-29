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

package views

import assets.messages.AddDetailsMessages
import controllers.routes
import forms.AdditionalPdfDetailsFormProvider
import models.{AdditionalPdfDetails, NormalMode}
import play.api.data.Form
import views.behaviours.QuestionViewBehavioursNew
import views.html.AddDetailsView

class AddDetailsViewSpec extends QuestionViewBehavioursNew[AdditionalPdfDetails] {

  override def beforeEach(): Unit = {
    super.beforeEach()

  }

  object Selectors extends BaseCSSSelectors{
    val link = "#value > p:nth-child(3) > a"
  }

  val messageKeyPrefix = "addDetails"

  val form = new AdditionalPdfDetailsFormProvider()()

  val view = injector.instanceOf[AddDetailsView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages,frontendAppConfig)

  def createHirerView = () => view(form, NormalMode)(hirerFakeRequest, messages,frontendAppConfig)

  def createViewUsingForm = (form: Form[AdditionalPdfDetails]) => view(form, NormalMode)(workerFakeRequest, messages,frontendAppConfig)

  "AddDetails view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = false)

    behave like pageWithBackLink(createView)

    behave like pageWithTextFields(
      createViewUsingForm,
      messageKeyPrefix,
      routes.PDFController.onSubmit(NormalMode).url,
      "completedBy", "client", "job", "reference"
    )

    lazy val document = asDocument(createView())

    "have the correct title" in {
      document.title mustBe title(AddDetailsMessages.title)
    }

    "have the correct heading" in {
      document.title mustBe title(AddDetailsMessages.heading)
    }

    "have the correct first label" in {
      document.select(Selectors.label(2)).text mustBe AddDetailsMessages.fileName
    }

    "have the correct second label" in {
      document.select(Selectors.label(3)).text mustBe AddDetailsMessages.name
    }

    "have the correct third label" in {
      document.select(Selectors.label(4)).text mustBe AddDetailsMessages.clientName
    }

    "have the correct fourth label" in {
      document.select(Selectors.label(5)).text mustBe AddDetailsMessages.role
    }

    "have the correct fifth label" in {
      document.select(Selectors.labelNthChild(6)).text mustBe AddDetailsMessages.reference
    }

    lazy val documentHirer = asDocument(createHirerView())

    "have the correct third label for the Hirer" in {
      documentHirer.select(Selectors.label(4)).text mustBe AddDetailsMessages.orgName
    }
  }
}
