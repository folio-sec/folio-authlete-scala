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
  IntrospectionResponse,
  RevocationRequest,
  RevocationResponse,
  TokenRequest,
  TokenResponse
}
import com.folio.authlete.json.Jackson
import org.scalatest.BeforeAndAfterAll
import org.scalatest.EitherValues._
import org.scalatest.funspec.AsyncFunSpec
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import java.nio.file.{Files, Paths}
import scala.concurrent.duration.DurationInt
import scala.util.chaining.scalaUtilChainingOps

/**
  * Tests that actually sends requests to remote Authlete servers.
  * The test data are saved encrypted in the `testdata.json.gpg` file.
  */
class IntegrationTests extends AsyncFunSpec with BeforeAndAfterAll {

  private lazy val testData: Map[String, String] = {
    import scala.jdk.CollectionConverters._
    Jackson.mapper.readValue(
      Files.newInputStream(Paths.get("./testdata.json")),
      classOf[java.util.Map[String, String]]
    ).asScala.toMap
  }

  private lazy val config =
    Config(testData("authlete.service.apiKey"), testData("authlete.service.apiSecret"), 10.seconds)
  private lazy val backend = AsyncHttpClientFutureBackend()
  private lazy val service = AuthleteService(backend, config)

  override def beforeAll(): Unit = {
    println(testData)
  }

  describe("clientGet") {
    it("responds successfully") {
      for {
        response <- service.clientGet(testData("authlete.client.clientId"))
      } yield {
        val result = response.body.value
        assert(result.getClientId == testData("authlete.client.clientId").toLong)
        assert(result.getClientSecret == testData("authlete.client.clientSecret"))
        assert(result.getClientName == testData("test.clientGet.expected.name"))
      }
    }

    it("responds error when given a false client id") {
      for {
        response <- service.clientGet("non-existent")
      } yield {
        val result = response.body.left.value
        assert(result.getResultCode == "A001216")
      }
    }
  }

  describe("authorization") {
    it("issues token by authorization-flow") {
      val clientId = testData("authlete.client.clientId")
      val redirectUri = testData("authlete.client.redirectUri")
      val subject = "john"

      for {
        authRes <- service.authorization(
          new AuthorizationRequest().setParameters(s"response_type=code&client_id=$clientId&redirect_uri=$redirectUri")
        )
        authResBody = authRes.body.value
        issueRes <- service.authorizationIssue(
          new AuthorizationIssueRequest().tap(_.setTicket(authResBody.getTicket).setSubject(subject))
        )
        issueResBody = issueRes.body.value
        tokenRes <- service.token(new TokenRequest()
          .setParameters(
            s"grant_type=authorization_code&client_id=$clientId&code=${issueResBody.getAuthorizationCode}&redirect_uri=$redirectUri"
          ))
        tokenResBody = tokenRes.body.value

        introspectRes <- service.introspection(new IntrospectionRequest().setToken(tokenResBody.getAccessToken))
        introspectResBody = introspectRes.body.value

        revokeRes <- service.revocation(new RevocationRequest().setParameters(s"token=${tokenResBody.getAccessToken}"))
        revokeResBody = revokeRes.body.value
      } yield {
        assert(tokenResBody.getAction == TokenResponse.Action.OK)
        assert(tokenResBody.getSubject == subject)

        assert(introspectResBody.getAction == IntrospectionResponse.Action.OK)

        assert(revokeResBody.getAction == RevocationResponse.Action.OK)
      }
    }

    it("can fail authorization") {
      val clientId = testData("authlete.client.clientId")
      val redirectUri = testData("authlete.client.redirectUri")

      for {
        authRes <- service.authorization(
          new AuthorizationRequest().setParameters(s"response_type=code&client_id=$clientId&redirect_uri=$redirectUri")
        )
        authResBody = authRes.body.value
        failRes <- service.authorizationFail(new AuthorizationFailRequest().setTicket(authResBody.getTicket))
        failResBody = failRes.body.value
      } yield {
        assert(failResBody.getAction == AuthorizationFailResponse.Action.LOCATION)
      }
    }
  }
}
