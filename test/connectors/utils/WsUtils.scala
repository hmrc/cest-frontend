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

package connectors.utils

import play.api.libs.ws.ahc.AhcWSResponse
import play.api.libs.ws.ahc.cache.{CacheableHttpResponseBodyPart, CacheableHttpResponseStatus}
import play.shaded.ahc.io.netty.handler.codec.http.DefaultHttpHeaders
import play.shaded.ahc.org.asynchttpclient.Response
import play.shaded.ahc.org.asynchttpclient.uri.Uri

trait WsUtils {

  def wsResponse(status: Int, responseBody: String) = {

    val respBuilder = new Response.ResponseBuilder()
    respBuilder.accumulate(new CacheableHttpResponseBodyPart(responseBody.getBytes(), true))
    respBuilder.accumulate(new DefaultHttpHeaders().add("Content-Type", "application/json"))
    respBuilder.accumulate(new CacheableHttpResponseStatus(Uri.create("http://localhost:8080"), status, "", "json"))
    new AhcWSResponse(respBuilder.build())

  }
}
