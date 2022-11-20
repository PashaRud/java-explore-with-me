CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(256) NOT NULL UNIQUE,
    email VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title  VARCHAR(256) NOT NULL UNIQUE,
    pinned BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR(1000)               NOT NULL,
    description        VARCHAR(9999)               NOT NULL,
    category_id        BIGINT,
    title              VARCHAR(256)                NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    initiator_id       BIGINT                      NOT NULL,
    lat                FLOAT                       NOT NULL,
    lon                FLOAT                       NOT NULL,
    paid               BOOLEAN                     NOT NULL,
    participant_limit  INT                         NOT NULL,
    request_moderation BOOLEAN                     NOT NULL,
    state              VARCHAR(256)                NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS events_compilations
(
    compilation_id bigint NOT NULL,
    event_id       bigint NOT NULL,
    CONSTRAINT pk_events_compilations PRIMARY KEY (compilation_id, event_id),
    FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created      timestamp WITHOUT TIME ZONE NOT NULL,
    event_id     bigint                      NOT NULL,
    requester_id bigint                      NOT NULL,
    status       varchar(50)                 NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (requester_id) REFERENCES users (id)
);