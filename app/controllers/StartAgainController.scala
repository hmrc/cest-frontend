/*
 * Copyright 2022 HM Revenue & Customs
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

package controllers

import config.FrontendAppConfig
import controllers.actions.IdentifierAction
import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CheckYourAnswersService
import views.html.StartAgainView

class StartAgainController @Inject()(identify: IdentifierAction,
                                     override val controllerComponents: MessagesControllerComponents,
                                     view: StartAgainView,
                                     checkYourAnswersService: CheckYourAnswersService,
                                     implicit val appConfig: FrontendAppConfig) extends BaseController {

  def redirectToGovUk: Action[AnyContent] = identify { implicit request =>
    Redirect(appConfig.govUkStartPageUrl).withNewSession
  }

  def redirectToDisclaimer: Action[AnyContent] = identify { implicit request =>
    Redirect(routes.IndexController.onPageLoad).withNewSession
  }

  def somethingWentWrong: Action[AnyContent] = identify { implicit request =>
    Conflict(view(appConfig))
  }
}
