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

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.actions.{DataRetrievalAction, IdentifierAction}
import javax.inject.Inject
import models.{NormalMode, UserAnswers}
import navigation.SetupNavigator
import pages.IndexPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CompareAnswerService
import uk.gov.hmrc.http.cache.client.CacheMap

class IndexController @Inject()(override val navigator: SetupNavigator,
                                identify: IdentifierAction,
                                getData: DataRetrievalAction,
                                cache: DataCacheConnector,
                                override val controllerComponents: MessagesControllerComponents,
                                override val compareAnswerService: CompareAnswerService,
                                override val dataCacheConnector: DataCacheConnector,
                                implicit val appConfig: FrontendAppConfig)
  extends BaseNavigationController with FeatureSwitching {

  def onPageLoad(c: Option[String] = None, lang: Option[String] = None): Action[AnyContent] = {
    (identify andThen getData).async { implicit request =>
    val userAnswers = request.userAnswers.fold(UserAnswers(new CacheMap(request.internalId, Map())))(x => x)
    cache.save(userAnswers.cacheMap).map(
      _ => Redirect(navigator.nextPage(IndexPage, NormalMode, c, lang)(userAnswers))
    )
  }
  }
}
