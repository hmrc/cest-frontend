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

package navigation

import javax.inject.{Inject, Singleton}

import config.FrontendAppConfig
import controllers.sections.control.{routes => controlRoutes}
import controllers.sections.exit.{routes => exitRoutes}
import controllers.sections.financialRisk.{routes => financialRiskRoutes}
import controllers.sections.partParcel.{routes => partParcelRoutes}
import controllers.sections.personalService.{routes => personalServiceRoutes}
import controllers.sections.setup.{routes => setupRoutes}
import models.ArrangedSubstitute.{No, YesClientAgreed, YesClientNotAgreed}
import models._
import pages._
import pages.sections.personalService._
import pages.sections.setup._

@Singleton
class QuestionDeletionLookup @Inject()(implicit appConfig: FrontendAppConfig) {

  def getPagesToRemove(currentPage: QuestionPage[_]): UserAnswers => List[QuestionPage[_]] = {
    pagesToRemove.getOrElse(currentPage,_ => List.empty)
  }

  private val personalService = List(ArrangedSubstitutePage, WouldWorkerPaySubstitutePage, RejectSubstitutePage, DidPaySubstitutePage, NeededToPayHelperPage)

  private val pagesToRemove: Map[QuestionPage[_], UserAnswers => List[QuestionPage[_]]] = Map(
    //Setup Section
    WorkerUsingIntermediaryPage -> (answers => answers.get(WorkerUsingIntermediaryPage) match {
      case Some(Answers(false, _)) =>
        List(IsWorkForPrivateSectorPage, TurnoverOverPage, EmployeesOverPage, BalanceSheetOverPage)
      case _ => List.empty
    }),
    IsWorkForPrivateSectorPage -> (answers => {
      answers.get(IsWorkForPrivateSectorPage) match {
        case Some(Answers(false, _)) => List(TurnoverOverPage, EmployeesOverPage, BalanceSheetOverPage)
        case _ => List.empty
      }
    }),
    TurnoverOverPage -> (_ => List(EmployeesOverPage, BalanceSheetOverPage)),
    EmployeesOverPage -> (_ => List(BalanceSheetOverPage)),
    ContractStartedPage -> (_ => personalService),
    //Personal Service Section
    ArrangedSubstitutePage -> (answers => {
      answers.get(ArrangedSubstitutePage) match {
        case Some(Answers(No, _)) => List(DidPaySubstitutePage, NeededToPayHelperPage)
        case Some(Answers(YesClientAgreed, _)) => List(WouldWorkerPaySubstitutePage, RejectSubstitutePage, NeededToPayHelperPage)
        case Some(Answers(YesClientNotAgreed, _)) => List(WouldWorkerPaySubstitutePage, RejectSubstitutePage, DidPaySubstitutePage)
        case _ => List.empty
      }
    }),
    DidPaySubstitutePage -> (answers => {
      answers.get(DidPaySubstitutePage) match {
        case Some(Answers(true, _)) => List(NeededToPayHelperPage)
        case _ => List.empty
      }
    }),
    RejectSubstitutePage -> (answers => {
      answers.get(RejectSubstitutePage) match {
        case Some(Answers(true, _)) => List(WouldWorkerPaySubstitutePage)
        case _ => List(NeededToPayHelperPage)
      }
    }),
    WouldWorkerPaySubstitutePage -> (answers => {
      answers.get(WouldWorkerPaySubstitutePage) match {
        case Some(Answers(true, _)) => List(NeededToPayHelperPage)
        case _ => List.empty
      }
    })
  )

}
