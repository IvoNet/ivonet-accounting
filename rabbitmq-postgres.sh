#!/usr/bin/env bash

mkdir -k ./volumes/postgres/setup 2>/dev/null
cp -v ./postgres_setup.sql ./volumes/postgres/setup

docker-compose up -d rabbitmq mongo postgres

mvn clean install -Dspring.profiles.active=rabbitmq_postgres,rabbitmq

if [ $? -ne 0 ]; then
    echo "Maven failed"
    docker-compose down
    exit 1
fi

QRY_PROFILE=rabbitmq_postgres CMD_PROFILE=rabbitmq docker-compose up -d cmd qry

docker-compose logs -f