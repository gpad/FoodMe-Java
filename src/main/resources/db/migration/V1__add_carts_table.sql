CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table carts
(
    id uuid not null DEFAULT uuid_generate_v4(),
    PRIMARY KEY (id)
);
