#!/usr/bin/env bash

mkdir -p ./volumes/postgresql/setup 2>/dev/null
cp -v ./postgres_setup.sql ./volumes/postgresql/setup

docker-compose up -d postgres mongo rabbitmq

if [ -n "$1" ]; then
    echo "Omitting CQRS application(s) in docker-compose"
    docker-compose logs -f
    exit 0
fi

sleep 5

mvn clean install -Dspring.profiles.active=postgres

if [ $? -ne 0 ]; then
    echo "Maven failed"
    docker-compose down
    exit 1
fi

QRY_PROFILE=postgres docker-compose up -d cmd qry

docker-compose logs -f