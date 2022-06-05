#!/usr/bin/env bash

docker-compose up -d rabbitmq mongo mariadb

if [ -n "$1" ]; then
    echo "Omitting CQRS application(s) in docker-compose"
    docker-compose logs -f
    exit 0
fi

mvn clean install

if [ $? -ne 0 ]; then
    echo "Maven failed"
    docker-compose down
    exit 1
fi

docker-compose up -d cmd qry

docker-compose logs -f