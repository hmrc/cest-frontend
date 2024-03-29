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

package controllers.sections.financialRisk

import config.FrontendAppConfig
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import forms.sections.financialRisk.MaterialsFormProvider
import javax.inject.Inject
import models.Mode
import navigation.FinancialRiskNavigator
import pages.sections.financialRisk.MaterialsPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CompareAnswerService
import views.html.sections.financialRisk.MaterialsView

import scala.concurrent.Future

class MaterialsController @Inject()(override val dataCacheConnector: DataCacheConnector,
                                    override val navigator: FinancialRiskNavigator,
                                    identify: IdentifierAction,
                                    getData: DataRetrievalAction,
                                    requireData: DataRequiredAction,
                                    requireUserType: UserTypeRequiredAction,
                                    formProvider: MaterialsFormProvider,
                                    override val controllerComponents: MessagesControllerComponents,
                                    view: MaterialsView,
                                    override val compareAnswerService: CompareAnswerService,
                                    implicit val appConfig: FrontendAppConfig)
  extends BaseNavigationController {

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData andThen requireUserType) { implicit request =>
    Ok(view(fillForm(MaterialsPage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(view(formWithErrors, mode))),
      value => redirect(mode, value, MaterialsPage)
    )
  }
}
