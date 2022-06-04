#!/usr/bin/env bash

docker-compose up -d rabbitmq mongo postgres
docker-compose logs -f