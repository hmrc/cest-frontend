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

package connectors

import javax.inject.Inject
import repositories.SessionRepository
import uk.gov.hmrc.http.cache.client.CacheMap

import scala.concurrent.{ExecutionContext, Future}

class MongoCacheConnector @Inject()(sessionRepository: SessionRepository)(implicit ec: ExecutionContext) extends DataCacheConnector {

  def save[A](cacheMap: CacheMap): Future[CacheMap] = {
    sessionRepository.upsert(cacheMap).map{_ => cacheMap}
  }

  def fetch(cacheId: String): Future[Option[CacheMap]] =
    sessionRepository.get(cacheId)
}

trait DataCacheConnector {
  def save[A](cacheMap: CacheMap): Future[CacheMap]

  def fetch(cacheId: String): Future[Option[CacheMap]]
}
