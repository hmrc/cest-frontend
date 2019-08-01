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

package controllers.sections.partParcel

import config.FrontendAppConfig
import config.featureSwitch.{FeatureSwitching, OptimisedFlow}
import controllers.actions._
import controllers.BaseController
import forms.IdentifyToStakeholdersFormProvider
import javax.inject.Inject

import connectors.DataCacheConnector
import models.{IdentifyToStakeholders, Mode}
import navigation.OldNavigator
import pages.sections.partParcel.IdentifyToStakeholdersPage
import play.api.data.Form
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import play.twirl.api.HtmlFormat
import services.{CheckYourAnswersService, CompareAnswerService, DecisionService}
import views.html.sections.partParcel.IdentifyToStakeholdersView
import views.html.subOptimised.sections.partParcel.{IdentifyToStakeholdersView => SubOptimisedIdentifyToStakeholdersView}

import scala.concurrent.Future

class IdentifyToStakeholdersController @Inject()(identify: IdentifierAction,
                                                 getData: DataRetrievalAction,
                                                 requireData: DataRequiredAction,
                                                 formProvider: IdentifyToStakeholdersFormProvider,
                                                 controllerComponents: MessagesControllerComponents,
                                                 optimisedView: IdentifyToStakeholdersView,
                                                 subOptimisedView: SubOptimisedIdentifyToStakeholdersView,
                                                 checkYourAnswersService: CheckYourAnswersService,
                                                 compareAnswerService: CompareAnswerService,
                                                 dataCacheConnector: DataCacheConnector,
                                                 decisionService: DecisionService,
                                                 navigator: OldNavigator,
                                                 implicit val appConfig: FrontendAppConfig) extends BaseController(
  controllerComponents,compareAnswerService,dataCacheConnector,navigator,decisionService) with FeatureSwitching {

  val form: Form[IdentifyToStakeholders] = formProvider()

  private def view(form: Form[IdentifyToStakeholders], mode: Mode)(implicit request: Request[_]): HtmlFormat.Appendable =
    if(isEnabled(OptimisedFlow)) optimisedView(form, mode) else subOptimisedView(form, mode)

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(fillForm(IdentifyToStakeholdersPage, form), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    form.bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(formWithErrors, mode))),
      value => {
        redirect(mode,value, IdentifyToStakeholdersPage, callDecisionService = true)
      }
    )
  }
}
