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
    logger.info(s"Authlete Request /client/get; ClientId: `$clientId`")
    baseRequest
      .get(baseUri.withPath("api", "client", "get", clientId))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[Client])
          logger.info(s"Authlete Response /client/get; ClientId: `${body.getClientId}`")
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /client/get; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def authorization(request: AuthorizationRequest): F[Response[Either[ApiResponse, AuthorizationResponse]]] = {
    logger.info(s"Authlete Request /auth/authorization")
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationResponse])
          logger.info(
            s"Authlete Response /auth/authorization; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/authorization; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def authorizationIssue(request: AuthorizationIssueRequest)
      : F[Response[Either[ApiResponse, AuthorizationIssueResponse]]] = {
    logger.info(s"Authlete Request /auth/authorization/issue")
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization", "issue"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationIssueResponse])
          logger.info(
            s"Authlete Response /auth/authorization/issue; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}`"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/authorization/issue; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def authorizationFail(request: AuthorizationFailRequest)
      : F[Response[Either[ApiResponse, AuthorizationFailResponse]]] = {
    logger.info(s"Authlete Request /auth/authorization/fail")
    baseRequest
      .post(baseUri.withPath("api", "auth", "authorization", "fail"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[AuthorizationFailResponse])
          logger.info(
            s"Authlete Response /auth/authorization/fail; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}`"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/authorization/fail; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def token(request: TokenRequest): F[Response[Either[ApiResponse, TokenResponse]]] = {
    logger.info(s"Authlete Request /auth/token; ClientId: `${request.getClientId}`")
    baseRequest
      .post(baseUri.withPath("api", "auth", "token"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[TokenResponse])
          logger.info(
            s"Authlete Response /auth/token; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}`"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/token; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def introspection(request: IntrospectionRequest): F[Response[Either[ApiResponse, IntrospectionResponse]]] = {
    logger.info(s"Authlete Request /auth/introspection")
    baseRequest
      .post(baseUri.withPath("api", "auth", "introspection"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[IntrospectionResponse])
          logger.info(
            s"Authlete Response /auth/introspection; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}`"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/introspection; {}", body)
          Left(body)
      }
      .send(backend)
  }

  override def revocation(request: RevocationRequest): F[Response[Either[ApiResponse, RevocationResponse]]] = {
    logger.info(s"Authlete Request /auth/revocation; ClientId: `${request.getClientId}`")
    baseRequest
      .post(baseUri.withPath("api", "auth", "revocation"))
      .body(json.renderJson(request))
      .mapResponse {
        case Right(value) =>
          val body = json.parseJson(value, classOf[RevocationResponse])
          logger.info(
            s"Authlete Response /auth/revocation; Action: `${body.getAction}`, ResultMessage: `${body.getResultMessage}`"
          )
          Right(body)
        case Left(value) =>
          val body = json.parseJson(value, classOf[ApiResponse])
          logger.info("Authlete Response /auth/revocation; {}", body)
          Left(body)
      }
      .send(backend)
  }
}
