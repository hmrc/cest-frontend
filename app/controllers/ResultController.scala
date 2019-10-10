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

package controllers

import javax.inject.Inject

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.actions._
import forms.{DeclarationFormProvider, DownloadPDFCopyFormProvider}
import models.{NormalMode, Timestamp}
import navigation.CYANavigator
import pages.{ResultPage, Timestamp}
import play.api.data.Form
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.{CheckYourAnswersService, CompareAnswerService, OptimisedDecisionService}
import utils.UserAnswersUtils

class ResultController @Inject()(identify: IdentifierAction,
                                 getData: DataRetrievalAction,
                                 requireData: DataRequiredAction,
                                 controllerComponents: MessagesControllerComponents,

                                 formProvider: DeclarationFormProvider,
                                 formProviderPDF: DownloadPDFCopyFormProvider,
                                 navigator: CYANavigator,
                                 dataCacheConnector: DataCacheConnector,
                                 time: Timestamp,
                                 compareAnswerService: CompareAnswerService,
                                 optimisedDecisionService: OptimisedDecisionService,
                                 checkYourAnswersService: CheckYourAnswersService,
                                 implicit val appConfig: FrontendAppConfig) extends BaseNavigationController(
  controllerComponents,compareAnswerService,dataCacheConnector,navigator) with FeatureSwitching with UserAnswersUtils {

  private val resultFormPDF: Form[Boolean] = formProviderPDF()

  def onPageLoad: Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    val timestamp = compareAnswerService.optimisedConstructAnswers(request,time.timestamp(),Timestamp)
    dataCacheConnector.save(timestamp.cacheMap).flatMap { _ =>
      optimisedDecisionService.determineResultView().map {
        case Right(result) => Ok(result)
        case Left(err) => InternalServerError(err)
      }
    }
  }

  def onSubmit: Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    resultFormPDF.bindFromRequest().fold(
      formWithErrors => {
        optimisedDecisionService.determineResultView(Some(formWithErrors)).map {
          case Right(result) => BadRequest(result)
          case Left(err) => InternalServerError(err)
        }
      },
      answer => {
        redirect[Boolean](NormalMode, answer, ResultPage, callDecisionService = false)
      }
    )
  }
}
