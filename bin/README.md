# Hibernate 6 + Spring Boot 3 + Postgres + Flyway Starter

A tiny, production-minded starter with entities, repositories, service, REST endpoints, Flyway migrations, and Docker Compose.

## Quick start (Docker)

```bash
docker compose up -d --build
# Wait ~10–20s for the DB healthcheck and app start
curl -s localhost:8080/actuator/health || true
```

## Sample API

### Create a customer
```bash
curl -X POST localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"email":"alice@example.com","name":"Alice"}'
```

### Get customer with orders
```bash
curl localhost:8080/api/customers/1
```

### Place an order
```bash
curl -X POST localhost:8080/api/customers/1/orders \
  -H 'Content-Type: application/json' \
  -d '{"amount": 199.99}'
```

### List customers (paginated)
```bash
curl 'localhost:8080/api/customers?status=ACTIVE&page=0&size=10'
```

## Local dev (without Docker)
- Ensure Postgres is running and update `application.yml` if needed.
- Build: `mvn -q -DskipTests package`
- Run: `java -jar target/hibernate-starter-0.0.1-SNAPSHOT.jar`

## Notes
- `ddl-auto=validate`: DDL is managed by **Flyway** (see `src/main/resources/db/migration`).
- Batch settings enabled for better write throughput.
- `open-in-view=false`: fetch needed graph inside service layer.
- Sequences use `allocationSize=50` (match Flyway sequence INCREMENT).

## Enable L2 Cache (optional)
Add Ehcache + props and annotate entities. (Ask if you want this wired in.)

Enjoy! ✨
