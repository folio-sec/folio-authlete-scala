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
package com.folio.authlete.json

import com.authlete.common.dto.{ClientExtension, Pair, Scope, Service}
import com.authlete.common.types.ServiceProfile
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.folio.authlete.Json

object Jackson extends Json {

  final val mapper: JsonMapper = JsonMapper.builder()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    // Serialization fails because Authlete's default model is an overloaded implementation of both Array and Set/Iterable.
    // For this reason, set it to ignore to use only one of the interfaces.
    .addMixIn(classOf[ClientExtension], classOf[ClientExtensionMixin])
    .addMixIn(classOf[Service], classOf[ServiceMixin])
    .addMixIn(classOf[Scope], classOf[ScopeMixin])
    .build()

  abstract class ClientExtensionMixin {
    @JsonProperty("requestableScopes") def setRequestableScopes(scopes: Array[String]): ClientExtension
    @JsonIgnore def setRequestableScopes(scopes: java.util.Set[String]): ClientExtension
  }

  abstract class ServiceMixin {
    @JsonProperty("supportedServiceProfiles") def setSupportedServiceProfiles(scopes: Array[ServiceProfile]): Service
    @JsonIgnore def setSupportedServiceProfiles(scopes: java.lang.Iterable[ServiceProfile]): Service
  }

  abstract class ScopeMixin {
    @JsonProperty("attributes") def setAttributes(scopes: Array[Pair]): Scope
    @JsonIgnore def setAttributes(scopes: java.lang.Iterable[Pair]): Scope
  }

  override def parseJson[T](json: String, cls: Class[T]): T = mapper.readValue(json, cls)
  override def renderJson(body: Any): String = mapper.writeValueAsString(body)
}
