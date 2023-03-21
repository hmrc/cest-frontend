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

package controllers.actions

import base.GuiceAppSpecBase
import controllers.ControllerSpecBase
import models.UserAnswers
import models.requests.{DataRequest, IdentifierRequest, OptionalDataRequest}
import play.api.mvc.Results.Redirect
import uk.gov.hmrc.http.cache.client.CacheMap

import scala.concurrent.{ExecutionContext, Future}

trait FakeDataRquiredAction extends GuiceAppSpecBase with DataRequiredAction with ControllerSpecBase {

  val langToReturn: Option[String]
  val cookiesBlockedToReturn: Option[String]
  val userAnswersToReturn: Option[String]

  override implicit protected def executionContext: ExecutionContext = ec

  override protected def refine[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] = {
    val cookiesBlocked: Option[String] = request.queryString.get ("c").map (s => s.headOption.getOrElse ("") )
    val lang: Option[String] = request.queryString.get ("lang").map (s => s.headOption.getOrElse (""))
    userAnswersToReturn match {
      case None if cookiesBlocked.isDefined => Future.successful(Left(Redirect(controllers.errors.routes.CookiesBlockedController.onPageLoad(languageChanger(lang)))))
      case None => Future.successful(Left(Redirect(controllers.routes.IndexController.onPageLoad(Some("1")))))
      case Some(data) => Future.successful(Right(DataRequest(request.request, request.internalId, data)))
    }
  }

  def languageChanger(lang: Option[String]): Option[String] = {
    lang.fold[Option[String]](None)(l => Some(s"?lang=${services.language(l)}"))
  }

}