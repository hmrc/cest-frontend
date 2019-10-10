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

package controllers.sections.financialRisk

import javax.inject.Inject

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import forms.sections.financialRisk.HowWorkerIsPaidFormProvider
import models.Mode
import models.requests.DataRequest
import models.sections.financialRisk.HowWorkerIsPaid
import navigation.FinancialRiskNavigator
import pages.sections.financialRisk.HowWorkerIsPaidPage
import play.api.data.Form
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import play.twirl.api.HtmlFormat
import services.{CheckYourAnswersService, CompareAnswerService}
import views.html.sections.financialRisk.HowWorkerIsPaidView

import scala.concurrent.Future

class HowWorkerIsPaidController @Inject()(identify: IdentifierAction,
                                          getData: DataRetrievalAction,
                                          requireData: DataRequiredAction,
                                          formProvider: HowWorkerIsPaidFormProvider,
                                          controllerComponents: MessagesControllerComponents,
                                          optimisedView: HowWorkerIsPaidView,
                                          checkYourAnswersService: CheckYourAnswersService,
                                          compareAnswerService: CompareAnswerService,
                                          dataCacheConnector: DataCacheConnector,
                                          navigator: FinancialRiskNavigator,
                                          implicit val appConfig: FrontendAppConfig) extends BaseNavigationController(
  controllerComponents,compareAnswerService,dataCacheConnector,navigator) with FeatureSwitching {

  private def view(form: Form[HowWorkerIsPaid], mode: Mode)(implicit request: DataRequest[_]): HtmlFormat.Appendable = {
    optimisedView(form, mode)
  }

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(fillForm(HowWorkerIsPaidPage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(formWithErrors, mode))),
      value => redirect(mode,value,HowWorkerIsPaidPage)
    )
  }
}
