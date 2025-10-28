# Hibernate 6 + Spring Boot 3 + Postgres + Flyway + L2 Cache + Testcontainers

## Quick start (Docker)
```bash
docker compose up -d --build
# app on :8080 when DB becomes healthy
```

## Sample API
```bash
# Create a customer
curl -X POST localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"email":"alice@example.com","name":"Alice"}'

# Get full customer (with orders)
curl localhost:8080/api/customers/1

# Place an order
curl -X POST localhost:8080/api/customers/1/orders \
  -H 'Content-Type: application/json' \
  -d '{"amount": 199.99}'

# List customers (paginated)
curl 'localhost:8080/api/customers?status=ACTIVE&page=0&size=10'

# DTO Projection: summaries
curl 'localhost:8080/api/customers/summaries?status=ACTIVE&page=0&size=10'
```

## L2 Cache
**Default: Ehcache 3 (JCache)**
- Enabled in `application.yml` with region factory `org.hibernate.cache.jcache.JCacheRegionFactory`.
- Entities and collections annotated with `@Cache(READ_WRITE)`.
- Ehcache config: `src/main/resources/ehcache.xml` (adjust sizes/TTLs).

**Optional: Infinispan**
```bash
mvn -P infinispan -DskipTests package
SPRING_PROFILES_ACTIVE=infinispan java -jar target/hibernate-starter-0.0.1-SNAPSHOT.jar
```
- Config: `src/main/resources/hibernate-infinispan.xml`
- Spring profile props: `application-infinispan.yml`.

## Testcontainers
Run integration tests against ephemeral Postgres:
```bash
mvn -q test
```

## Notes
- `ddl-auto=validate`; schema via Flyway (`db/migration`).
- Batch settings enabled for better write throughput.
- `open-in-view=false` → fetch inside service layer.
