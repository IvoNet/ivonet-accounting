#!/usr/bin/env bash

profile=kafka
database=postgres

mkdir -p ./volumes/postgresql/setup 2>/dev/null
cp -v ./postgres_setup.sql ./volumes/postgresql/setup

docker-compose up -d zookeeper ${profile} mongo ${database}

mvn clean install -Dspring.profiles.active=${profile}_${database},${profile}

if [ $? -ne 0 ]; then
    echo "Maven failed"
    docker-compose down
    exit 1
fi

QRY_PROFILE=${profile}_${database} CMD_PROFILE=${profile} docker-compose up -d cmd qry

docker-compose logs -f