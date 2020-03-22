CREATE TABLE IF NOT EXISTS planet
(
    id bigint PRIMARY KEY,
    external_id text,
    image       blob,
    name        text,
    status      text
);