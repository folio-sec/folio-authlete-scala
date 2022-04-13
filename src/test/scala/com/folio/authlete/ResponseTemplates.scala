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

/**
  * The examples of this class is copied from [[ https://docs.authlete.com/en/shared/2.2.19 Authlete official document]].
  */
object ResponseTemplates {

  val badRequest: String = """
      |{
      |  "resultCode": "A001201",
      |  "resultMessage": "[A001201] /auth/authorization, TLS must be used."
      |}
      |""".stripMargin

  val clientGet: String = """
      |{
      |  "applicationType": "WEB",
      |  "attributes": [
      |    {
      |      "key": "attribute1-key",
      |      "value": "attribute1-value"
      |    },
      |    {
      |      "key": "attribute2-key",
      |      "value": "attribute2-value"
      |    }
      |  ],
      |  "authTimeRequired": false,
      |  "bcUserCodeRequired": false,
      |  "clientId": 26478243745571,
      |  "clientIdAlias": "my-client",
      |  "clientIdAliasEnabled": true,
      |  "clientName": "My client",
      |  "clientSecret": "gXz97ISgLs4HuXwOZWch8GEmgL4YMvUJwu3er_kDVVGcA0UOhA9avLPbEmoeZdagi9yC_-tEiT2BdRyH9dbrQQ",
      |  "clientType": "CONFIDENTIAL",
      |  "createdAt": 1639468356000,
      |  "defaultMaxAge": 0,
      |  "derivedSectorIdentifier": "my-client.example.com",
      |  "developer": "john",
      |  "dynamicallyRegistered": false,
      |  "frontChannelRequestObjectEncryptionRequired": false,
      |  "grantTypes": [
      |    "AUTHORIZATION_CODE",
      |    "REFRESH_TOKEN"
      |  ],
      |  "idTokenSignAlg": "RS256",
      |  "modifiedAt": 1639468356000,
      |  "number": 6164,
      |  "parRequired": false,
      |  "redirectUris": [
      |    "https://my-client.example.com/cb1",
      |    "https://my-client.example.com/cb2"
      |  ],
      |  "requestObjectEncryptionAlgMatchRequired": false,
      |  "requestObjectEncryptionEncMatchRequired": false,
      |  "requestObjectRequired": false,
      |  "responseTypes": [
      |    "CODE",
      |    "TOKEN"
      |  ],
      |  "serviceNumber": 5041,
      |  "subjectType": "PUBLIC",
      |  "tlsClientCertificateBoundAccessTokens": false,
      |  "tokenAuthMethod": "CLIENT_SECRET_BASIC"
      |}
      |""".stripMargin

  val authorization: String = """
      |{
      |  "type": "authorizationResponse",
      |  "resultCode": "A004001",
      |  "resultMessage": "[A004001] Authlete has successfully issued a ticket to the service (API Key = 21653835348762) for the authorization request from the client (ID = 26478243745571). [response_type=code, openid=false]",
      |  "acrEssential": false,
      |  "action": "INTERACTION",
      |  "client": {
      |    "applicationType": "WEB",
      |    "attributes": [
      |      {
      |        "key": "attribute1-key",
      |        "value": "attribute1-value"
      |      },
      |      {
      |        "key": "attribute2-key",
      |        "value": "attribute2-value"
      |      }
      |    ],
      |    "authTimeRequired": false,
      |    "bcUserCodeRequired": false,
      |    "clientId": 26478243745571,
      |    "clientIdAlias": "my-client",
      |    "clientIdAliasEnabled": true,
      |    "clientName": "My updated client",
      |    "clientSecret": "my_updated_client_secret",
      |    "clientType": "CONFIDENTIAL",
      |    "createdAt": 1639468356000,
      |    "defaultMaxAge": 0,
      |    "derivedSectorIdentifier": "my-client.example.com",
      |    "developer": "john",
      |    "dynamicallyRegistered": false,
      |    "frontChannelRequestObjectEncryptionRequired": false,
      |    "grantTypes": [
      |      "AUTHORIZATION_CODE",
      |      "REFRESH_TOKEN"
      |    ],
      |    "idTokenSignAlg": "RS256",
      |    "modifiedAt": 1639560232000,
      |    "number": 6164,
      |    "parRequired": false,
      |    "redirectUris": [
      |      "https://my-client.example.com/cb1",
      |      "https://my-client.example.com/cb2"
      |    ],
      |    "requestObjectEncryptionAlgMatchRequired": false,
      |    "requestObjectEncryptionEncMatchRequired": false,
      |    "requestObjectRequired": false,
      |    "responseTypes": [
      |      "CODE",
      |      "TOKEN"
      |    ],
      |    "serviceNumber": 5041,
      |    "subjectType": "PUBLIC",
      |    "tlsClientCertificateBoundAccessTokens": false,
      |    "tokenAuthMethod": "CLIENT_SECRET_BASIC"
      |  },
      |  "clientIdAliasUsed": false,
      |  "display": "PAGE",
      |  "maxAge": 0,
      |  "scopes": [
      |    {
      |      "defaultEntry": false,
      |      "description": "A permission to read your history.",
      |      "name": "history.read"
      |    },
      |    {
      |      "defaultEntry": false,
      |      "description": "A permission to read your timeline.",
      |      "name": "timeline.read"
      |    }
      |  ],
      |  "service": {
      |    "accessTokenDuration": 3600,
      |    "accessTokenType": "Bearer",
      |    "allowableClockSkew": 0,
      |    "apiKey": 21653835348762,
      |    "apiSecret": "uE4NgqeIpuSV_XejQ7Ds3jsgA1yXhjR1MXJ1LbPuyls",
      |    "attributes": [
      |      {
      |        "key": "attribute1-key",
      |        "value": "attribute1-value"
      |      },
      |      {
      |        "key": "attribute2-key",
      |        "value": "attribute2-value"
      |      }
      |    ],
      |    "authorizationEndpoint": "https://my-service.example.com/authz",
      |    "authorizationResponseDuration": 0,
      |    "backchannelAuthReqIdDuration": 0,
      |    "backchannelBindingMessageRequiredInFapi": false,
      |    "backchannelPollingInterval": 0,
      |    "backchannelUserCodeParameterSupported": false,
      |    "claimShortcutRestrictive": false,
      |    "clientIdAliasEnabled": true,
      |    "clientsPerDeveloper": 0,
      |    "createdAt": 1639373421000,
      |    "dcrScopeUsedAsRequestable": false,
      |    "deviceFlowCodeDuration": 0,
      |    "deviceFlowPollingInterval": 0,
      |    "directAuthorizationEndpointEnabled": false,
      |    "directIntrospectionEndpointEnabled": false,
      |    "directJwksEndpointEnabled": false,
      |    "directRevocationEndpointEnabled": false,
      |    "directTokenEndpointEnabled": false,
      |    "directUserInfoEndpointEnabled": false,
      |    "dynamicRegistrationSupported": false,
      |    "errorDescriptionOmitted": false,
      |    "errorUriOmitted": false,
      |    "frontChannelRequestObjectEncryptionRequired": false,
      |    "grantManagementActionRequired": false,
      |    "hsmEnabled": false,
      |    "idTokenDuration": 0,
      |    "introspectionEndpoint": "https://my-service.example.com/introspection",
      |    "issSuppressed": false,
      |    "issuer": "https://my-service.example.com",
      |    "metadata": [
      |      {
      |        "key": "clientCount",
      |        "value": "1"
      |      }
      |    ],
      |    "missingClientIdAllowed": false,
      |    "modifiedAt": 1639559308000,
      |    "mutualTlsValidatePkiCertChain": false,
      |    "nbfOptional": false,
      |    "number": 5041,
      |    "parRequired": false,
      |    "pkceRequired": true,
      |    "pkceS256Required": false,
      |    "pushedAuthReqDuration": 0,
      |    "refreshTokenDuration": 3600,
      |    "refreshTokenDurationKept": false,
      |    "refreshTokenDurationReset": false,
      |    "refreshTokenKept": false,
      |    "requestObjectEncryptionAlgMatchRequired": false,
      |    "requestObjectEncryptionEncMatchRequired": false,
      |    "requestObjectRequired": false,
      |    "revocationEndpoint": "https://my-service.example.com/revocation",
      |    "scopeRequired": false,
      |    "serviceName": "My updated service",
      |    "serviceOwnerNumber": 2,
      |    "singleAccessTokenPerSubject": false,
      |    "supportedClaimTypes": [
      |      "NORMAL"
      |    ],
      |    "supportedDisplays": [
      |      "PAGE"
      |    ],
      |    "supportedGrantTypes": [
      |      "AUTHORIZATION_CODE",
      |      "REFRESH_TOKEN"
      |    ],
      |    "supportedIntrospectionAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "supportedResponseTypes": [
      |      "CODE"
      |    ],
      |    "supportedRevocationAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "supportedScopes": [
      |      {
      |        "defaultEntry": false,
      |        "description": "A permission to read your history.",
      |        "name": "history.read"
      |      },
      |      {
      |        "defaultEntry": false,
      |        "description": "A permission to read your timeline.",
      |        "name": "timeline.read"
      |      }
      |    ],
      |    "supportedTokenAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "tlsClientCertificateBoundAccessTokens": false,
      |    "tokenEndpoint": "https://my-service.example.com/token",
      |    "tokenExpirationLinked": false,
      |    "traditionalRequestObjectProcessingApplied": false,
      |    "unauthorizedOnClientConfigSupported": false,
      |    "userCodeLength": 0
      |  },
      |  "ticket": "hXoY87t_t23enrVHWxpXNP5FfVDhDypD3T6H6lt4IPA"
      |}
      |""".stripMargin

  val issue: String = """
      |{
      |  "resultCode": "A040001",
      |  "resultMessage": "[A040001] The authorization request was processed successfully.",
      |  "accessTokenDuration": 0,
      |  "accessTokenExpiresAt": 0,
      |  "action": "LOCATION",
      |  "authorizationCode": "Xv_su944auuBgc5mfUnxXayiiQU9Z4-T_Yae_UfExmo",
      |  "responseContent": "https://my-client.example.com/cb1?code=Xv_su944auuBgc5mfUnxXayiiQU9Z4-T_Yae_UfExmo&iss=https%3A%2F%2Fmy-service.example.com"
      |}
      |""".stripMargin

  val fail: String = """
      |{
      |  "resultCode": "A004201",
      |  "resultMessage": "[A004201] The authorization request from the service does not contain 'parameters' parameter.",
      |  "acrEssential": false,
      |  "action": "BAD_REQUEST",
      |  "clientIdAliasUsed": false,
      |  "maxAge": 0,
      |  "responseContent": "{\\\"error_description\\\":\\\"[A004201] The authorization request from the service does not contain 'parameters' parameter.\\\",\\\"error\\\":\\\"invalid_request\\\",\\\"error_uri\\\":\\\"https://docs.authlete.com/#A004201\\\"}",
      |  "service": {
      |    "accessTokenDuration": 3600,
      |    "accessTokenType": "Bearer",
      |    "allowableClockSkew": 0,
      |    "apiKey": 21653835348762,
      |    "apiSecret": "uE4NgqeIpuSV_XejQ7Ds3jsgA1yXhjR1MXJ1LbPuyls",
      |    "attributes": [
      |      {
      |        "key": "attribute1-key",
      |        "value": "attribute1-value"
      |      },
      |      {
      |        "key": "attribute2-key",
      |        "value": "attribute2-value"
      |      }
      |    ],
      |    "authorizationEndpoint": "https://my-service.example.com/authz",
      |    "authorizationResponseDuration": 0,
      |    "backchannelAuthReqIdDuration": 0,
      |    "backchannelBindingMessageRequiredInFapi": false,
      |    "backchannelPollingInterval": 0,
      |    "backchannelUserCodeParameterSupported": false,
      |    "claimShortcutRestrictive": false,
      |    "clientIdAliasEnabled": true,
      |    "clientsPerDeveloper": 0,
      |    "createdAt": 1639373421000,
      |    "dcrScopeUsedAsRequestable": false,
      |    "deviceFlowCodeDuration": 0,
      |    "deviceFlowPollingInterval": 0,
      |    "directAuthorizationEndpointEnabled": false,
      |    "directIntrospectionEndpointEnabled": false,
      |    "directJwksEndpointEnabled": false,
      |    "directRevocationEndpointEnabled": false,
      |    "directTokenEndpointEnabled": false,
      |    "directUserInfoEndpointEnabled": false,
      |    "dynamicRegistrationSupported": false,
      |    "errorDescriptionOmitted": false,
      |    "errorUriOmitted": false,
      |    "frontChannelRequestObjectEncryptionRequired": false,
      |    "grantManagementActionRequired": false,
      |    "hsmEnabled": false,
      |    "idTokenDuration": 0,
      |    "introspectionEndpoint": "https://my-service.example.com/introspection",
      |    "issSuppressed": false,
      |    "issuer": "https://my-service.example.com",
      |    "metadata": [
      |      {
      |        "key": "clientCount",
      |        "value": "1"
      |      }
      |    ],
      |    "missingClientIdAllowed": false,
      |    "modifiedAt": 1639559308000,
      |    "mutualTlsValidatePkiCertChain": false,
      |    "nbfOptional": false,
      |    "number": 5041,
      |    "parRequired": false,
      |    "pkceRequired": true,
      |    "pkceS256Required": false,
      |    "pushedAuthReqDuration": 0,
      |    "refreshTokenDuration": 3600,
      |    "refreshTokenDurationKept": false,
      |    "refreshTokenDurationReset": false,
      |    "refreshTokenKept": false,
      |    "requestObjectEncryptionAlgMatchRequired": false,
      |    "requestObjectEncryptionEncMatchRequired": false,
      |    "requestObjectRequired": false,
      |    "revocationEndpoint": "https://my-service.example.com/revocation",
      |    "scopeRequired": false,
      |    "serviceName": "My updated service",
      |    "serviceOwnerNumber": 2,
      |    "singleAccessTokenPerSubject": false,
      |    "supportedClaimTypes": [
      |      "NORMAL"
      |    ],
      |    "supportedDisplays": [
      |      "PAGE"
      |    ],
      |    "supportedGrantTypes": [
      |      "AUTHORIZATION_CODE",
      |      "REFRESH_TOKEN"
      |    ],
      |    "supportedIntrospectionAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "supportedResponseTypes": [
      |      "CODE"
      |    ],
      |    "supportedRevocationAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "supportedScopes": [
      |      {
      |        "defaultEntry": false,
      |        "description": "A permission to read your history.",
      |        "name": "history.read"
      |      },
      |      {
      |        "defaultEntry": false,
      |        "description": "A permission to read your timeline.",
      |        "name": "timeline.read"
      |      }
      |    ],
      |    "supportedTokenAuthMethods": [
      |      "CLIENT_SECRET_BASIC"
      |    ],
      |    "tlsClientCertificateBoundAccessTokens": false,
      |    "tokenEndpoint": "https://my-service.example.com/token",
      |    "tokenExpirationLinked": false,
      |    "traditionalRequestObjectProcessingApplied": false,
      |    "unauthorizedOnClientConfigSupported": false,
      |    "userCodeLength": 0
      |  }
      |}
      |""".stripMargin

  val token: String = """
      |{
      |  "resultCode": "A050001",
      |  "resultMessage": "[A050001] The token request (grant_type=authorization_code) was processed successfully.",
      |  "accessToken": "C4SrUTijIj2IxqE1xBASr3dxQWgso3BpY49g8CyjGjQ",
      |  "accessTokenDuration": 3600,
      |  "accessTokenExpiresAt": 1640252942736,
      |  "action": "OK",
      |  "clientAttributes": [
      |    {
      |      "key": "attribute1-key",
      |      "value": "attribute1-value"
      |    },
      |    {
      |      "key": "attribute2-key",
      |      "value": "attribute2-value"
      |    }
      |  ],
      |  "clientId": 26478243745571,
      |  "clientIdAlias": "my-client",
      |  "clientIdAliasUsed": false,
      |  "grantType": "AUTHORIZATION_CODE",
      |  "refreshToken": "60k0cZ38sJcpTgdxvG9Sqa-3RG5AmGExGpFB-1imSxo",
      |  "refreshTokenDuration": 3600,
      |  "refreshTokenExpiresAt": 1640252942736,
      |  "responseContent": "{\\\"access_token\\\":\\\"C4SrUTijIj2IxqE1xBASr3dxQWgso3BpY49g8CyjGjQ\\\",\\\"refresh_token\\\":\\\"60k0cZ38sJcpTgdxvG9Sqa-3RG5AmGExGpFB-1imSxo\\\",\\\"scope\\\":\\\"history.read timeline.read\\\",\\\"token_type\\\":\\\"Bearer\\\",\\\"expires_in\\\":3600}",
      |  "scopes": [
      |    "history.read",
      |    "timeline.read"
      |  ],
      |  "serviceAttributes": [
      |    {
      |      "key": "attribute1-key",
      |      "value": "attribute1-value"
      |    },
      |    {
      |      "key": "attribute2-key",
      |      "value": "attribute2-value"
      |    }
      |  ],
      |  "subject": "john"
      |}
      |""".stripMargin

  val introspection: String = """
      |{
      |  "resultCode": "A056001",
      |  "resultMessage": "[A056001] The access token is valid.",
      |  "action": "OK",
      |  "clientAttributes": [
      |    {
      |      "key": "attribute1-key",
      |      "value": "attribute1-value"
      |    },
      |    {
      |      "key": "attribute2-key",
      |      "value": "attribute2-value"
      |    }
      |  ],
      |  "clientId": 26478243745571,
      |  "clientIdAlias": "my-client",
      |  "clientIdAliasUsed": false,
      |  "existent": true,
      |  "expiresAt": 1640416873000,
      |  "refreshable": true,
      |  "responseContent": "Bearer error=\"invalid_request\"",
      |  "scopes": [
      |    "history.read",
      |    "timeline.read"
      |  ],
      |  "serviceAttributes": [
      |    {
      |      "key": "attribute1-key",
      |      "value": "attribute1-value"
      |    },
      |    {
      |      "key": "attribute2-key",
      |      "value": "attribute2-value"
      |    }
      |  ],
      |  "subject": "john",
      |  "sufficient": true,
      |  "usable": true
      |}
      |""".stripMargin

  val revocation: String = """
      |{
      |  "resultCode": "A113001",
      |  "resultMessage": "[A113001] The token has been revoked successfully.",
      |  "action": "OK"
      |}
      |""".stripMargin

  object Expected {
    val badRequestResponseCode: String = "A001201"
    val clientId: Long = 26478243745571L
    val ticket: String = "hXoY87t_t23enrVHWxpXNP5FfVDhDypD3T6H6lt4IPA"
    val authorizationCode: String = "Xv_su944auuBgc5mfUnxXayiiQU9Z4-T_Yae_UfExmo"
    val accessToken: String = "C4SrUTijIj2IxqE1xBASr3dxQWgso3BpY49g8CyjGjQ"
    val subject: String = "john"
  }
}
