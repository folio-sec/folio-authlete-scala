# folio-authlete-scala

A thin [Authlete](https://www.authlete.com/) client library for Scala2 by [FOLIO](https://folio-sec.com/)

# License

Apache 2.0

# Usage

## Prerequisites

It uses [sttp](https://github.com/softwaremill/sttp) as a http interface.
You'll need some sttp backend implementations you choose in your dependencies.

## Currently available endpoints

```
/api/client/get
/api/auth/authorization
/api/auth/authorization/issue
/api/auth/authorization/fail
/api/auth/token
/api/auth/introspection
/api/auth/revocation
```

The default host is `api.authlete.com`.
You can change it via `host` and `port` parameters in the `Config` object.

By default, it uses Jackson as a JSON parser.
You may provide your own one to the `Config`.

## Usage

```scala
val config = Config("apiKey", "apiSecret", Duration.Inf)
val sttpBackend = SttpBackendYouLike()
val service = AuthleteService(sttpBackend, config)

// Calls `/api/client/get` endpoint to get `Client`
val response = service.clientGet("clientId")
```


# Run integration tests

We save secrets for ci tests in `testdata.json`, following
[this](https://docs.github.com/en/actions/security-guides/encrypted-secrets#limits-for-secrets)
advice.
You need `gpg` to be installed to encrypt/decrypt it. Run `brew install gpg` to install it.

Encrypt: `gpg --batch --passphrase "${PASSPHRASE}" --symmetric --cipher-algo AES256 testdata.json`
Decrypt: `gpg --batch --decrypt --passphrase="${PASSPHRASE}" --output ./testdata.out.json testdata.json.gpg`

*To FOLIO developers:* gpg passphrase is saved in the LastPass shared folder.
