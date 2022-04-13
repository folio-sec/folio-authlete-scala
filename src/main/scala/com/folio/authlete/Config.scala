/*
 * Copyright 2022 FOLIO Co., Ltd.
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
package com.folio.authlete

import scala.concurrent.duration.Duration

final case class Config(
    host: String,
    port: Int,
    apiKey: String,
    apiSecret: String,
    requestTimeout: Duration
) {

  override def toString: String = {
    s"Config(authlete endpoint=$host:$port, api key=$apiKey, request timeout=$requestTimeout)"
  }
}

object Config {

  def apply(apiKey: String, apiSecret: String, requestTimeout: Duration): Config = Config(
    "api.authlete.com",
    443,
    apiKey,
    apiSecret,
    requestTimeout
  )
}
