SHELL := /bin/bash

.PHONY: build run stop logs clean test

build:
	docker compose build --no-cache

run:
	docker compose up -d --build

stop:
	docker compose down

logs:
	docker compose logs -f --tail=200

clean: stop
	docker volume rm -f hibernate-starter-updated_dbdata || true

test:
	mvn -q test
