#!/usr/bin/env bash

docker-compose up -d zookeeper kafka mongo postgres
docker-compose logs -f