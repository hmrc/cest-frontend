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

package controllers.errors

import config.FrontendAppConfig
import controllers.BaseController
import javax.inject.{Inject, Singleton}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import views.html.errors.SessionExpiredView

@Singleton
class SessionExpiredController @Inject()(val appConfig: FrontendAppConfig,
                                         override val controllerComponents: MessagesControllerComponents,
                                         expiredView: SessionExpiredView
                                        ) extends BaseController with I18nSupport {

  def onPageLoad: Action[AnyContent] = Action { implicit request =>
    Ok(expiredView(appConfig))
  }

  def onSubmit: Action[AnyContent] = Action { implicit request =>
    Redirect(controllers.routes.IndexController.onPageLoad())
  }
}
