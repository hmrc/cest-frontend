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

package navigation

import base.GuiceAppSpecBase
import controllers.sections.control.{routes => controlRoutes}
import controllers.sections.financialRisk.{routes => finacialRiskRoutes}
import models._
import pages._
import pages.sections.control.{ChooseWhereWorkPage, HowWorkIsDonePage, MoveWorkerPage, ScheduleOfWorkingHoursPage}

class ControlNavigatorSpec extends GuiceAppSpecBase {

  val emptyUserAnswers = UserAnswers("id")
  val navigator = new ControlNavigator

  def nextPage(fromPage: Page, userAnswers: UserAnswers = emptyUserAnswers) = navigator.nextPage(fromPage, NormalMode)(userAnswers)

  "ControlNavigator" when {

    "go from the MoveWorkerPage to the HowWorkIsDonePage" in {

      nextPage(MoveWorkerPage) mustBe controlRoutes.HowWorkIsDoneController.onPageLoad(NormalMode)
    }

    "go from the HowWorkIsDonePage to the ScheduleOfWorkingHoursPage" in {

      nextPage(HowWorkIsDonePage) mustBe controlRoutes.ScheduleOfWorkingHoursController.onPageLoad(NormalMode)
    }

    "go from the ScheduleOfWorkingHoursPage to the ChooseWhereWorkPage" in {

      nextPage(ScheduleOfWorkingHoursPage) mustBe controlRoutes.ChooseWhereWorkController.onPageLoad(NormalMode)
    }

    "go from the ChooseWhereWorkPage" when {

      "The normal flow is enabled go to the EquipmentExpensesPage" in {

        nextPage(ChooseWhereWorkPage) mustBe finacialRiskRoutes.EquipmentExpensesController.onPageLoad(NormalMode)
      }
    }
  }
}