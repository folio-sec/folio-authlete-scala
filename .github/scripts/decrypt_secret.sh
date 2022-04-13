#!/bin/sh

# The code is taken from https://docs.github.com/en/actions/security-guides/encrypted-secrets#limits-for-secrets

gpg --quiet --batch --yes --decrypt --passphrase="$IT_GPG_PASSPHRASE" --output testdata.json testdata.json.gpg
