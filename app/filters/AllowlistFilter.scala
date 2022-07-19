/*
 * Copyright 2022 HM Revenue & Customs
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

package filters

import akka.stream.Materializer
import config.FrontendAppConfig
import uk.gov.hmrc.allowlist.AkamaiAllowlistFilter
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Call, RequestHeader, Result}

import scala.concurrent.Future

@Singleton
class AllowlistFilter @Inject()(val appConfig: FrontendAppConfig, implicit val mat: Materializer) extends AkamaiAllowlistFilter {

  override lazy val allowlist: Seq[String] = appConfig.allowlistedIps
  override lazy val destination: Call = Call("GET", appConfig.shutterPage)
  override lazy val excludedPaths: Seq[Call] = appConfig.allowlistExcludedPaths

  override def apply(requestFunc: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    if(appConfig.allowlistEnabled) super.apply(requestFunc)(requestHeader) else requestFunc(requestHeader)
  }
}
