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
package com.folio.authlete.util

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
import com.folio.authlete.util.Show.{fromToString, ShowOps}

/**
  * Convert Authlete DTOs to strings.
  * Implementations in this class MUST NOT output the secrets.
  * Currently, `subject` will not be output since it can be a actual name.
  */
object ShowAuthleteDto {

  def showDtoHelper[A](getters: A => (String, String)*): Show[A] = { self: A =>
    self.getClass.getName + getters.map(g => g(self)).toMap.show
  }

  implicit val showApiResponse: Show[ApiResponse] = {
    showDtoHelper("ResultCode" -> _.getResultCode, "ResultMessage" -> _.getResultMessage)
  }

  implicit val showClient: Show[Client] = {
    showDtoHelper(
      "ClientId" -> _.getClientId.show,
      "ClientName" -> _.getClientName
    )
  }

  implicit val showAuthorizationRequest: Show[AuthorizationRequest] = {
    // `parameters` can contain sensitives like pkce challenge
    showDtoHelper()
  }

  implicit val showAuthorizationResponse: Show[AuthorizationResponse] = {
    implicit val s: Show[AuthorizationResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      x => "Action" -> x.getAction.show
    )
  }

  implicit val showAuthorizationIssueRequest: Show[AuthorizationIssueRequest] = {
    showDtoHelper()
  }

  implicit val showAuthorizationIssueResponse: Show[AuthorizationIssueResponse] = {
    implicit val showAction: Show[AuthorizationIssueResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      "Action" -> _.getAction.show
    )
  }

  implicit val showAuthorizationFailRequest: Show[AuthorizationFailRequest] = {
    implicit val showReason: Show[AuthorizationFailRequest.Reason] = fromToString
    showDtoHelper("Reason" -> _.getReason.show)
  }

  implicit val showAuthorizationFailResponse: Show[AuthorizationFailResponse] = {
    implicit val showAction: Show[AuthorizationFailResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      "Action" -> _.getAction.show
    )
  }

  implicit val showTokenRequest: Show[TokenRequest] = {
    showDtoHelper(
      "ClientId" -> _.getClientId
    )
  }

  implicit val showTokenResponse: Show[TokenResponse] = {
    implicit val showAction: Show[TokenResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      "Action" -> _.getAction.show,
      "ClientId" -> _.getClientId.show
    )
  }

  implicit val showIntrospectionRequest: Show[IntrospectionRequest] = {
    showDtoHelper()
  }

  implicit val showIntrospectionResponse: Show[IntrospectionResponse] = {
    implicit val showAction: Show[IntrospectionResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      "Action" -> _.getAction.show,
      "ClientId" -> _.getClientId.show
    )
  }

  implicit val showRevocationRequest: Show[RevocationRequest] = {
    showDtoHelper("ClientId" -> _.getClientId)
  }

  implicit val showRevocationResponse: Show[RevocationResponse] = {
    implicit val showAction: Show[RevocationResponse.Action] = fromToString
    showDtoHelper(
      "ResultCode" -> _.getResultCode,
      "ResultMessage" -> _.getResultMessage,
      "Action" -> _.getAction.show
    )
  }
}
