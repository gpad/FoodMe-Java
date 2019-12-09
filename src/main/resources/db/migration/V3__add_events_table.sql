CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table events
(
    id           uuid not null DEFAULT uuid_generate_v4(),
    aggregate_id uuid,
    version      int8,
    event_type   varchar,
    payload      json
);
