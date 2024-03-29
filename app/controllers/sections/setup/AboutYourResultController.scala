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

package controllers.sections.setup

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import javax.inject.Inject
import models.NormalMode
import navigation.SetupNavigator
import pages.sections.setup.AboutYourResultPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CompareAnswerService
import views.html.sections.setup.AboutYourResultView

class AboutYourResultController @Inject()(override val navigator: SetupNavigator,
                                          identify: IdentifierAction,
                                          getData: DataRetrievalAction,
                                          requireData: DataRequiredAction,
                                          override val controllerComponents: MessagesControllerComponents,
                                          view: AboutYourResultView,
                                          override val compareAnswerService: CompareAnswerService,
                                          override val dataCacheConnector: DataCacheConnector,
                                          implicit val appConfig: FrontendAppConfig)
  extends BaseNavigationController with FeatureSwitching {

  def onPageLoad(c: Option[String] = None, lang: Option[String] = None): Action[AnyContent] =
    (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(routes.AboutYourResultController.onSubmit(c, lang)))
  }


  def onSubmit(c: Option[String] = None, lang: Option[String] = None): Action[AnyContent] =
    (identify andThen getData andThen requireData) { implicit request =>
    Redirect(navigator.nextPage(AboutYourResultPage, NormalMode, c, lang)(request.userAnswers))
  }
}
