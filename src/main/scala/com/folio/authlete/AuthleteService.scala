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

import com.authlete.common.dto.{
  ApiResponse,
  AuthorizationFailRequest,
  AuthorizationFailResponse,
  AuthorizationIssueRequest,
  AuthorizationIssueResponse,
  AuthorizationRequest,
  AuthorizationResponse,
  Client,
  IntrospectionRequest,
  IntrospectionResponse,
  RevocationRequest,
  RevocationResponse,
  TokenRequest,
  TokenResponse
}
import sttp.client3.{Response, SttpBackend}

trait AuthleteService[F[_]] {

  /**
    * /api/client/get エンドポイント
    *
    * [[https://docs.authlete.com/#client-get-api]]
    */
  def clientGet(clientId: String): F[Response[Either[ApiResponse, Client]]]

  /**
    * /api/auth/authorization エンドポイント
    *
    * [[https://docs.authlete.com/#auth-authorization-api]]
    */
  def authorization(request: AuthorizationRequest): F[Response[Either[ApiResponse, AuthorizationResponse]]]

  /**
    * /api/auth/authorization/issue エンドポイント
    *
    * [[https://docs.authlete.com/#auth-authorization-issue-api]]
    */
  def authorizationIssue(request: AuthorizationIssueRequest)
      : F[Response[Either[ApiResponse, AuthorizationIssueResponse]]]

  /**
    * /api/auth/authorization/fail エンドポイント
    *
    * [[https://docs.authlete.com/#auth-authorization-fail-api]]
    */
  def authorizationFail(request: AuthorizationFailRequest): F[Response[Either[ApiResponse, AuthorizationFailResponse]]]

  /**
    * /api/auth/token エンドポイント
    *
    * [[https://docs.authlete.com/#token-endpoint]]
    */
  def token(request: TokenRequest): F[Response[Either[ApiResponse, TokenResponse]]]

  /**
    * /api/auth/introspection エンドポイント
    *
    * [[https://docs.authlete.com/#introspection-endpoint]]
    */
  def introspection(request: IntrospectionRequest): F[Response[Either[ApiResponse, IntrospectionResponse]]]

  /**
    * /api/auth/revocation エンドポイント
    *
    * [[https://docs.authlete.com/#revocation-endpoint]]
    */
  def revocation(request: RevocationRequest): F[Response[Either[ApiResponse, RevocationResponse]]]
}

object AuthleteService {

  def apply[F[_]](sttpBackend: SttpBackend[F, Any], config: Config): AuthleteService[F] = {
    apply(
      sttpBackend,
      json.Jackson,
      config
    )
  }

  def apply[F[_]](sttpBackend: SttpBackend[F, Any], json: Json, config: Config): AuthleteService[F] = {
    new AuthleteServiceImpl(sttpBackend, json, config)
  }
}
