# Accounting

This accounting service aims to provide a simple way to manage your accounting needs.

# Prerequisites

- docker installed
- docker-compose installed
- Java 17 installed
- Maven installed
- A good IDE you feel comfortable with
- Knowledge of CQRS and Event Sourcing
- [cqrs.core](https://github.com/IvoNet/cqrs.core) project installed in your local repository by cloning and building
  it. It is actually an integral part of this system

# Installation

# RabbitMQ

## Password

- Save this script as `pwd_enc`
- chmod +x `pwd_enc`

```shell
#!/usr/bin/env bash

function get_byte()
{
    local BYTE=$(head -c 1 /dev/random | tr -d '\0')

    if [ -z "$BYTE" ]; then
        BYTE=$(get_byte)
    fi

    echo "$BYTE"
}

function encode_password()
{
    BYTE1=$(get_byte)
    BYTE2=$(get_byte)
    BYTE3=$(get_byte)
    BYTE4=$(get_byte)

    SALT="${BYTE1}${BYTE2}${BYTE3}${BYTE4}"
    PASS="$SALT$1"
    TEMP=$(echo -n "$PASS" | openssl sha256 -binary)
    PASS="$SALT$TEMP"
    PASS=$(echo -n "$PASS" | base64)
    echo "$PASS"
}

encode_password $1
```