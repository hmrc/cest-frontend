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

package controllers.sections.partParcel

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import forms.sections.partAndParcel.BenefitsFormProvider
import javax.inject.Inject
import models.Mode
import navigation.PartAndParcelNavigator
import pages.sections.partParcel.BenefitsPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CompareAnswerService
import views.html.sections.partParcel.BenefitsView

import scala.concurrent.Future

class BenefitsController @Inject()(identify: IdentifierAction,
                                   getData: DataRetrievalAction,
                                   requireData: DataRequiredAction,
                                   requireUserType: UserTypeRequiredAction,
                                   formProvider: BenefitsFormProvider,
                                   override val controllerComponents: MessagesControllerComponents,
                                   view: BenefitsView,
                                   override val compareAnswerService: CompareAnswerService,
                                   override val dataCacheConnector: DataCacheConnector,
                                   override val navigator: PartAndParcelNavigator,
                                   implicit val appConfig: FrontendAppConfig)
  extends BaseNavigationController with FeatureSwitching {


  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData andThen requireUserType) { implicit request =>
    Ok(view(fillForm(BenefitsPage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(formWithErrors, mode))),
      value => {
        redirect(mode,value,BenefitsPage)
      }
    )
  }
}
