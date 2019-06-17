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

package services

import cats.implicits._
import config.featureSwitch.FeatureSwitching
import config.{FrontendAppConfig, SessionKeys}
import connectors.DecisionConnector
import controllers.routes
import forms.DeclarationFormProvider
import handlers.ErrorHandler
import javax.inject.{Inject, Singleton}
import models._
import models.requests.DataRequest
import pages.sections.exit.OfficeHolderPage
import pages.sections.setup.{IsWorkForPrivateSectorPage, WorkerUsingIntermediaryPage}
import play.api.data.Form
import play.api.i18n.Messages
import play.api.mvc.{AnyContent, Call, Request}
import play.mvc.Http.Status._
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.http.HeaderCarrier
import viewmodels.AnswerSection
import views.html.results.inside._
import views.html.results.inside.officeHolder.{OfficeHolderAgentView, OfficeHolderIR35View, OfficeHolderPAYEView}
import views.html.results.undetermined._
import views.html.subOptimised.results.{ControlView, SelfEmployedView}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OptimisedDecisionService @Inject()(decisionConnector: DecisionConnector,
                                         errorHandler: ErrorHandler,
                                         formProvider: DeclarationFormProvider,
                                         val officeAgency: OfficeHolderAgentView,
                                         val officeIR35: OfficeHolderIR35View,
                                         val officePAYE: OfficeHolderPAYEView,
                                         val undeterminedAgency: AgentUndeterminedView,
                                         val undeterminedIR35: IR35UndeterminedView,
                                         val undeterminedPAYE: PAYEUndeterminedView,
                                         val insideIR35Agent: AgentInsideView,
                                         val insideIR35Worker: IR35InsideView,
                                         val insideIR35Hirer: IR35InsideView,
                                         val insidePAYE: PAYEInsideView,
                                         val outsideAgentView: ControlView,
                                         val outsideIR35View: ControlView,
                                         val outsidePAYEView: SelfEmployedView,
                                         implicit val appConf: FrontendAppConfig) extends FeatureSwitching {

  def multipleDecisionCall()(implicit request: DataRequest[AnyContent], hc: HeaderCarrier): Future[Either[ErrorResponse, DecisionResponse]] = {
    val interview = Interview(request.userAnswers)
    (for {
      personalService <- decisionConnector.decideSection(interview, Interview.writesPersonalService)
      control <- decisionConnector.decideSection(interview, Interview.writesControl)
      financialRisk <- decisionConnector.decideSection(interview, Interview.writesFinancialRisk)
      partAndParcel <- decisionConnector.decideSection(interview, Interview.writesPartAndParcel)
      wholeInterview <- decisionConnector.decideSection(interview, Interview.writes)
    } yield wholeInterview).value.flatMap {
      case Left(Right(decision)) => logResult(decision, interview).map(_ => Right(decision))
      case Left(Left(error)) => Future.successful(Left(error))
      case Right(_) => Future.successful(Left(ErrorResponse(INTERNAL_SERVER_ERROR, "No decision reached")))
    }
  }

  private def logResult(decision: DecisionResponse, interview: Interview)
                       (implicit hc: HeaderCarrier, ec: ExecutionContext, rh: Request[_]): Future[Either[ErrorResponse, Boolean]] = {
    decision match {
      case response if response.result != ResultEnum.NOT_MATCHED => decisionConnector.log(interview, response)
      case _ => Future.successful(Left(ErrorResponse(NO_CONTENT, "No log needed")))
    }
  }

  lazy val version = appConf.decisionVersion
  lazy val resultForm: Form[Boolean] = formProvider()

  def determineResultView(answerSections: Seq[AnswerSection], formWithErrors: Option[Form[Boolean]] = None, printMode: Boolean = false,
                          additionalPdfDetails: Option[AdditionalPdfDetails] = None, timestamp: Option[String] = None)
                         (implicit request: DataRequest[_], messages: Messages): Html = {

    val result = request.session.get(SessionKeys.result).map(ResultEnum.withName).getOrElse(ResultEnum.NOT_MATCHED)

    val form: Form[Boolean] = formWithErrors.getOrElse(resultForm)
    val action: Call = routes.ResultController.onSubmit()

    val userType = request.userType

    val usingIntermediary = request.userAnswers.get(WorkerUsingIntermediaryPage).exists(answer => answer.answer)
    val privateSector = request.userAnswers.get(IsWorkForPrivateSectorPage).exists(answer => answer.answer)

    val officeHolderAnswer = request.userAnswers.get(OfficeHolderPage) match {
      case Some(answer) => answer.answer
      case _ => false
    }

    implicit val resultsDetails: ResultsDetails = ResultsDetails(form, action, officeHolderAnswer, privateSector, usingIntermediary, userType)

    result match {
      case ResultEnum.INSIDE_IR35 => resultViewInside
      case ResultEnum.EMPLOYED => resultViewInside
      case ResultEnum.OUTSIDE_IR35 => resultViewOutside(answerSections, formWithErrors, printMode, additionalPdfDetails, timestamp)
      case ResultEnum.SELF_EMPLOYED => resultViewOutside(answerSections, formWithErrors, printMode, additionalPdfDetails, timestamp)
      case ResultEnum.UNKNOWN => undetermined
      case ResultEnum.NOT_MATCHED => errorHandler.internalServerErrorTemplate
    }
  }

  def undetermined(implicit request: DataRequest[_], messages: Messages, result: ResultsDetails): Html = {

    (result.usingIntermediary, result.isAgent) match {

      case (_, true) => undeterminedAgency(result.form, result.action) /** AGENT **/
      case (true, _) => undeterminedIR35(result.form, result.action, result.privateSector) /** IR35 **/
      case _ => undeterminedPAYE(result.form, result.action) /** PAYE **/
    }
  }

  def resultViewOutside(answerSections: Seq[AnswerSection], formWithErrors: Option[Form[Boolean]] = None, printMode: Boolean = false,
                        additionalPdfDetails: Option[AdditionalPdfDetails] = None, timestamp: Option[String] = None)
                       (implicit request: DataRequest[_], messages: Messages, result: ResultsDetails): HtmlFormat.Appendable = {

    (result.usingIntermediary, result.isAgent) match {
      case (_, true) => outsideAgentView(answerSections,version,result.form,result.action,printMode,additionalPdfDetails,timestamp) /** AGENT **/
      case (true, _) => outsideIR35View(answerSections,version,result.form,result.action,printMode,additionalPdfDetails,timestamp) /** IR35 **/
      case _ => outsidePAYEView(answerSections,version,result.form,result.action,printMode,additionalPdfDetails,timestamp) /** PAYE **/
    }
  }

  def resultViewInside(implicit request: DataRequest[_], messages: Messages, result: ResultsDetails): HtmlFormat.Appendable = {

    if (result.officeHolderAnswer) officeHolder else insideIR35
  }

  def insideIR35(implicit request: DataRequest[_], messages: Messages, result: ResultsDetails): Html = {

    (result.usingIntermediary, result.userType) match {

      case (_, Some(UserType.Agency)) => insideIR35Agent(result.form, result.action) /** AGENT **/
      case (true, Some(UserType.Worker)) => insideIR35Worker(result.form, result.action, result.privateSector) /** WORKER **/
      case (true, _) => insideIR35Hirer(result.form, result.action, result.privateSector) /** HIRER **/
      case _ => insidePAYE(result.form, result.action) /** PAYE **/
    }
  }

  def officeHolder(implicit request: DataRequest[_], messages: Messages, result: ResultsDetails): Html = {

    (result.usingIntermediary, result.isAgent) match {

      case (_, true) => officeAgency(result.form, result.action) /** AGENT **/
      case (true, _) => officeIR35(result.form, result.action, result.privateSector) /** IR35 **/
      case _ => officePAYE(result.form, result.action) /** PAYE **/
    }
  }


}
