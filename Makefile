SHELL := /bin/bash

.PHONY: build run stop logs clean

build:
	docker compose build --no-cache

run:
	docker compose up -d --build

stop:
	docker compose down

logs:
	docker compose logs -f --tail=200

clean: stop
	docker volume rm -f hibernate-starter_dbdata || true
