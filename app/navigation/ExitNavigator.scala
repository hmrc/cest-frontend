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

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import controllers.routes._
import controllers.sections.personalService.{routes => personalServiceRoutes}
import controllers.sections.setup.{routes => setupRoutes}
import javax.inject.{Inject, Singleton}
import models._
import pages._
import pages.sections.exit.OfficeHolderPage
import pages.sections.setup._
import play.api.mvc.Call

@Singleton
class ExitNavigator @Inject()(implicit appConfig: FrontendAppConfig) extends Navigator with FeatureSwitching {

  def officeHolderRouteMap(mode: Mode): Map[Page, UserAnswers => Call] = {
    Map(OfficeHolderPage -> (answers =>
      if(answers.get(OfficeHolderPage).contains(true)) {
        ResultController.onPageLoad()
      } else {
        mode match {
          case CheckMode => CheckYourAnswersController.onPageLoad(Some(Section.earlyExit))
          case NormalMode => answers.get(ContractStartedPage) match {
                case Some(true) => personalServiceRoutes.ArrangedSubstituteController.onPageLoad(NormalMode)
                case Some(_) => personalServiceRoutes.RejectSubstituteController.onPageLoad(NormalMode)
                case _ => setupRoutes.ContractStartedController.onPageLoad(NormalMode)
              }
        }
      }))
  }

  override def nextPage(page: Page, mode: Mode, c: Option[String] = None, lang: Option[String] = None): UserAnswers => Call = {
    officeHolderRouteMap(mode).getOrElse(page, _ => IndexController.onPageLoad())
  }
}
