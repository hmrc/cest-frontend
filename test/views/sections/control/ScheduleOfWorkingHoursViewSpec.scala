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

package views.sections.control

import assets.messages.{ScheduleOfWorkingHoursMessages, SubHeadingMessages}
import forms.sections.control.ScheduleOfWorkingHoursFormProvider
import models.NormalMode
import models.sections.control.ScheduleOfWorkingHours
import play.api.data.Form
import play.api.mvc.Request
import views.behaviours.ViewBehavioursNew
import views.html.sections.control.ScheduleOfWorkingHoursView

class ScheduleOfWorkingHoursViewSpec extends ViewBehavioursNew {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "worker.scheduleOfWorkingHours"

  val form = new ScheduleOfWorkingHoursFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[ScheduleOfWorkingHoursView]

  def createView = () => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(workerFakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: Request[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "ScheduleOfWorkingHours view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ScheduleOfWorkingHoursMessages.OptimisedWorker.title, Some(SubHeadingMessages.control))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (ScheduleOfWorkingHoursMessages.OptimisedWorker.heading)
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.yesClientDecides
        document.select(Selectors.multichoice(2)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.noWorkerDecides
        document.select(Selectors.multichoice(3)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.partly
        document.select(Selectors.multichoice(4)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.notApplicable
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ScheduleOfWorkingHoursMessages.OptimisedHirer.title, Some(SubHeadingMessages.control))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (ScheduleOfWorkingHoursMessages.OptimisedHirer.heading)
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedHirer.yesClientDecides
        document.select(Selectors.multichoice(2)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedHirer.noWorkerDecides
        document.select(Selectors.multichoice(3)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedHirer.partly
        document.select(Selectors.multichoice(4)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedHirer.notApplicable

      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeRequest))

      "have the correct title" in {
        document.title mustBe title(ScheduleOfWorkingHoursMessages.OptimisedWorker.title, Some(SubHeadingMessages.control))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text must include (ScheduleOfWorkingHoursMessages.OptimisedWorker.heading)
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.yesClientDecides
        document.select(Selectors.multichoice(2)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.noWorkerDecides
        document.select(Selectors.multichoice(3)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.partly
        document.select(Selectors.multichoice(4)).text mustBe ScheduleOfWorkingHoursMessages.OptimisedWorker.notApplicable

      }
    }
  }

  "ScheduleOfWorkingHours view" when {
    "rendered" must {
      "contain radio buttons for the value" in {
        val doc = asDocument(createViewUsingForm(form))
        for (option <- ScheduleOfWorkingHours.options) {
          assertContainsRadioButton(doc, idHelper(ScheduleOfWorkingHours.options, option), "value", option.value, isChecked = false)
        }
      }
    }

    for(option <- ScheduleOfWorkingHours.options) {
      s"rendered with a value of '${option.value}'" must {
        s"have the '${option.value}' radio button selected" in {
          val doc = asDocument(createViewUsingForm(form.bind(Map("value" -> s"${option.value}"))))
          assertContainsRadioButton(doc, idHelper(ScheduleOfWorkingHours.options, option), "value", option.value, isChecked = true)

          for(unselectedOption <- ScheduleOfWorkingHours.options.filterNot(o => o == option)) {
            assertContainsRadioButton(doc, idHelper(ScheduleOfWorkingHours.options, unselectedOption), "value", unselectedOption.value, isChecked = false)
          }
        }
      }
    }
  }
}
