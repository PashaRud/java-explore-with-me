CREATE TABLE IF NOT EXISTS statistics
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY,
    uri       VARCHAR(255)                NOT NULL,
    app       VARCHAR(255)                NOT NULL,
    ip        VARCHAR(128)                NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL
);