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

package controllers

import config.featureSwitch.FeatureSwitching
import config.{FrontendAppConfig, SessionKeys}
import controllers.actions._
import handlers.ErrorHandler
import models.{AdditionalPdfDetails, DecisionResponse, Timestamp}
import pages.{CustomisePDFPage, Timestamp}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.{CheckYourAnswersService, DecisionService, EncryptionService}
import utils.SessionUtils._
import viewmodels.ResultPrintPreview

import javax.inject.Inject

class PrintPreviewController @Inject()(identify: IdentifierAction,
                                       getData: DataRetrievalAction,
                                       requireData: DataRequiredAction,
                                       requireUserType: UserTypeRequiredAction,
                                       override val controllerComponents: MessagesControllerComponents,
                                       decisionService: DecisionService,
                                       checkYourAnswersService: CheckYourAnswersService,
                                       encryptionService: EncryptionService,
                                       time: Timestamp,
                                       errorHandler: ErrorHandler,
                                       implicit val appConfig: FrontendAppConfig)
  extends BaseController with FeatureSwitching {

  def onPageLoad(): Action[AnyContent] = (identify andThen getData andThen requireData andThen requireUserType) { implicit request =>

    val pdfDetails = request.userAnswers.get(CustomisePDFPage).map(answer => encryptionService.decryptDetails(answer)).getOrElse(AdditionalPdfDetails())
    val timestamp = time.timestamp(request.userAnswers.get(Timestamp))

    request.session.getModel[DecisionResponse](SessionKeys.decisionResponse) match {
      case Some(decision) =>
        decisionService.determineResultView(
          decision = decision,
          resultMode = ResultPrintPreview,
          answerSections = checkYourAnswersService.sections,
          additionalPdfDetails = Some(pdfDetails),
          timestamp = Some(timestamp)
        )

        match {
          case Right(result) => Ok(result)
          case Left(_) => Redirect(controllers.routes.StartAgainController.somethingWentWrong)
        }
      case _ => Redirect(controllers.routes.StartAgainController.somethingWentWrong)
    }
  }
}
