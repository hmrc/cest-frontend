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

import connectors.DataCacheConnector
import models.requests.DataRequest
import models.{Section, _}
import navigation.Navigator
import pages.{BusinessOnOwnAccountSectionChangeWarningPage, PersonalServiceSectionChangeWarningPage, QuestionPage}
import play.api.libs.json.{Reads, Writes}
import play.api.mvc.{AnyContent, Result}
import services.CompareAnswerService

import scala.concurrent.Future

trait BaseNavigationController extends BaseController {

  val compareAnswerService: CompareAnswerService
  val dataCacheConnector: DataCacheConnector
  val navigator: Navigator

  def redirect[T](mode: Mode,
                  value: T,
                  page: QuestionPage[T])(implicit request: DataRequest[AnyContent],
                                         reads: Reads[T],
                                         writes: Writes[T]): Future[Result] = {

    val currentAnswer = request.userAnswers.get(page)

    // If this is the first redirect since the Personal Service warning page was displayed
    // And, it is in CheckMode. And, the Answer has not changed.
    // Then redirect back to CYA
    val answerUnchanged = mode == CheckMode && currentAnswer.contains(value)

    val personalWarning = request.userAnswers.get(PersonalServiceSectionChangeWarningPage).isDefined
    val boOAWarning = request.userAnswers.get(BusinessOnOwnAccountSectionChangeWarningPage).isDefined

    //Remove the Personal Service warning page viewed flag from the request
    val req = DataRequest(request.request, request.internalId,
    request.userAnswers.remove(PersonalServiceSectionChangeWarningPage).remove(BusinessOnOwnAccountSectionChangeWarningPage))

    val answers = compareAnswerService.constructAnswers(req, value, page)

    dataCacheConnector.save(answers.cacheMap).flatMap { _ =>
      (answerUnchanged, personalWarning, boOAWarning) match {
        case (true, true, _) => Future.successful(Redirect(routes.CheckYourAnswersController.onPageLoad(Some(Section.personalService))))
        case (true, _, true) => Future.successful(Redirect(routes.CheckYourAnswersController.onPageLoad(Some(Section.businessOnOwnAccount))))
        case _ => Future.successful(Redirect(navigator.nextPage(page, mode)(answers)))
      }
    }
  }
}
