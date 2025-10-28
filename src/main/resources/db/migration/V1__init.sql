-- Flyway V1: initial schema
-- Sequences with allocation size 50 to match JPA @SequenceGenerator(allocationSize = 50)
CREATE SEQUENCE IF NOT EXISTS customer_seq INCREMENT BY 50 START WITH 1;
CREATE SEQUENCE IF NOT EXISTS order_seq INCREMENT BY 50 START WITH 1;

CREATE TABLE IF NOT EXISTS customers (
    id BIGINT PRIMARY KEY DEFAULT nextval('customer_seq'),
    email VARCHAR(320) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    status VARCHAR(32) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS ix_customers_status ON customers(status);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_seq'),
    customer_id BIGINT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    amount NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ix_orders_customer_id ON orders(customer_id);
