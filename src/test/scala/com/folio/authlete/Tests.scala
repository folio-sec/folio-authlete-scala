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
  AuthorizationFailRequest,
  AuthorizationFailResponse,
  AuthorizationIssueRequest,
  AuthorizationRequest,
  IntrospectionRequest,
  RevocationRequest,
  TokenRequest
}
import org.scalatest.EitherValues._
import org.scalatest.funspec.AnyFunSpec
import sttp.client3.testing.SttpBackendStub
import sttp.model.StatusCode

import scala.concurrent.duration.Duration

class Tests extends AnyFunSpec {

  private val config = Config("testApiKey", "testApiSecret", Duration.Inf)

  private val backend = SttpBackendStub.synchronous
    .whenRequestMatches(_.uri.path.startsWith(Seq("api", "client", "get")))
    .thenRespond(ResponseTemplates.clientGet)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "authorization"))
    .thenRespond(ResponseTemplates.authorization)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "authorization", "issue"))
    .thenRespond(ResponseTemplates.issue)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "authorization", "fail"))
    .thenRespond(ResponseTemplates.fail)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "token"))
    .thenRespond(ResponseTemplates.token)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "introspection"))
    .thenRespond(ResponseTemplates.introspection)
    .whenRequestMatches(_.uri.path == Seq("api", "auth", "revocation"))
    .thenRespond(ResponseTemplates.revocation)
  private val service = AuthleteService(backend, config)

  private val erroneousBackend = SttpBackendStub.synchronous
    .whenAnyRequest
    .thenRespond(ResponseTemplates.badRequest, StatusCode.BadRequest)
  private val erroneousService = AuthleteService(erroneousBackend, config)

  describe("clientGet") {
    it("responds successfully") {
      val response = service.clientGet("clientId")

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getClientId == ResponseTemplates.Expected.clientId)
    }

    it("responds an error") {
      val response = erroneousService.clientGet("clientId")

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("authorization") {
    it("responds successfully") {
      val response = service.authorization(new AuthorizationRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getTicket == ResponseTemplates.Expected.ticket)
    }

    it("responds an error") {
      val response = erroneousService.authorization(new AuthorizationRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("issue") {
    it("responds successfully") {
      val response = service.authorizationIssue(new AuthorizationIssueRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getAuthorizationCode == ResponseTemplates.Expected.authorizationCode)
    }

    it("responds an error") {
      val response = erroneousService.authorizationIssue(new AuthorizationIssueRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("fail") {
    it("responds successfully") {
      val response = service.authorizationFail(new AuthorizationFailRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getAction == AuthorizationFailResponse.Action.BAD_REQUEST)
    }

    it("responds an error") {
      val response = erroneousService.authorizationFail(new AuthorizationFailRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("token") {
    it("responds successfully") {
      val response = service.token(new TokenRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getAccessToken == ResponseTemplates.Expected.accessToken)
    }

    it("responds an error") {
      val response = erroneousService.token(new TokenRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("introspection") {
    it("responds successfully") {
      val response = service.introspection(new IntrospectionRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getSubject == ResponseTemplates.Expected.subject)
    }

    it("responds an error") {
      val response = erroneousService.introspection(new IntrospectionRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }

  describe("revocation") {
    it("responds successfully") {
      val response = service.revocation(new RevocationRequest)

      assert(response.code == StatusCode.Ok)
      assert(response.body.value.getResultCode == "A113001")
    }

    it("responds an error") {
      val response = erroneousService.revocation(new RevocationRequest)

      assert(response.code == StatusCode.BadRequest)
      assert(response.body.left.value.getResultCode == ResponseTemplates.Expected.badRequestResponseCode)
    }
  }
}
