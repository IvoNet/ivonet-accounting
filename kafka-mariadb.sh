#!/usr/bin/env bash

docker-compose up -d zookeeper kafka mongo mariadb

mvn clean install -Pkafka_mariadb,kafka
if [ $? -ne 0 ]; then
    echo "Maven failed"
    exit 1
fi

QRY_PROFILE=kafka_mariadb CMD_PROFILE=kafka docker-compose up -d cmd qry

docker-compose logs -f