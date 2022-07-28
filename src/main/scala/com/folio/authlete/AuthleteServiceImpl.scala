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
import com.folio.authlete.util.Show
import com.folio.authlete.util.Show.ShowOps
import com.folio.authlete.util.ShowAuthleteDto._
import org.slf4j.LoggerFactory
import sttp.client3.{asString, basicRequest, Response, SttpBackend}
import sttp.model.{MediaType, Uri}

final private[authlete] class AuthleteServiceImpl[F[_]](
    backend: SttpBackend[F, Any],
    json: Json,
    config: Config
) extends AuthleteService[F] {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val baseRequest = basicRequest
    .auth.basic(config.apiKey, config.apiSecret)
    .contentType(MediaType.ApplicationJson)
    .response(asString)
    .readTimeout(config.requestTimeout)
  private val baseUri = Uri("https", config.host, config.port)

  override def clientGet(clientId: String): F[Response[Either[ApiResponse, Client]]] = {
    val api = "/client/get"
    logRequest(api, "ClientId" -> clientId)
    baseRequest
      .get(baseUri.withPath("api", "client", "get", clientId))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[Client])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def authorization(request: AuthorizationRequest): F[Response[Either[ApiResponse, AuthorizationResponse]]] = {
    val api = "/auth/authorization"
    logRequest(api, request)
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationResponse])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def authorizationIssue(request: AuthorizationIssueRequest)
      : F[Response[Either[ApiResponse, AuthorizationIssueResponse]]] = {
    val api = "/auth/authorization/issue"
    logRequest(api, request)
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization", "issue"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationIssueResponse])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def authorizationFail(request: AuthorizationFailRequest)
      : F[Response[Either[ApiResponse, AuthorizationFailResponse]]] = {
    val api = "/auth/authorization/fail"
    logRequest(api, request)
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization", "fail"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationFailResponse])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def token(request: TokenRequest): F[Response[Either[ApiResponse, TokenResponse]]] = {
    val api = "/auth/token"
    logRequest(api, request)
    baseRequest
      .post(baseUri.withPath("api", "auth", "token"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[TokenResponse])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def introspection(request: IntrospectionRequest): F[Response[Either[ApiResponse, IntrospectionResponse]]] = {
    val api = "/auth/introspection"
    logRequest(api, request)
    baseRequest
      .post(baseUri.withPath("api", "auth", "introspection"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[IntrospectionResponse])
          logResponse(api, body)
          Right(body)
        case Left(value) => handleError(api, value)
      }
      .send(backend)
  }

  override def revocation(request: RevocationRequest): F[Response[Either[ApiResponse, RevocationResponse]]] = {
    post(request, classOf[RevocationResponse], "auth", "revocation")
  }

  private def post[A: Show, B: Show](request: A, cls: Class[B], path: String*): F[Response[Either[ApiResponse, B]]] = {
    val apiName = path.mkString("/", "/", "")
    logRequest(apiName, request)
    baseRequest
      // Authlete endpoint begins with path `/api`
      .post(baseUri.withPath("api", path: _*))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) => handleResponse[B](apiName, value, cls)
        case Left(value)  => handleError(apiName, value)
      }
      .send(backend)
  }

  private def handleResponse[A: Show](api: String, bodyStr: String, cls: Class[A]): Right[Nothing, A] = {
    val body = json.parseJson(bodyStr, cls)
    logResponse(api, body)
    Right(body)
  }

  private def handleError(api: String, bodyStr: String): Left[ApiResponse, Nothing] = {
    val body = json.parseJson(bodyStr, classOf[ApiResponse])
    logResponse(api, body)
    Left(body)
  }

  private def logRequest[A: Show](api: String, body: A): Unit = {
    logger.info(s"Authlete Request {}; {}", api, body.show)
  }

  private def logResponse[A: Show](api: String, body: A): Unit = {
    logger.info(s"Authlete Response {}; {}", api, body.show)
  }
}
