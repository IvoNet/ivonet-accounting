#!/usr/bin/env bash

docker-compose up -d rabbitmq mongo mariadb

mvn clean install -Prabbitmq_mariadb,rabbitmq

if [ $? -ne 0 ]; then
    echo "Maven failed"
    docker-compose down
    exit 1
fi

QRY_PROFILE=rabbitmq_mariadb CMD_PROFILE=rabbitmq docker-compose up -d cmd qry

docker-compose logs -f