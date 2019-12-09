CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table cart_items
(
    id          uuid not null DEFAULT uuid_generate_v4(),
    product_id  uuid,
    description VARCHAR,
    qty         int,
    PRIMARY KEY (id)
);
