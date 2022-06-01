-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE customer
(
    id        INT8 GENERATED ALWAYS AS IDENTITY NOT NULL,
    chat_id   INT8 NOT NULL,
    PRIMARY KEY (id)
);

-- changeset liquibase:2
ALTER TABLE customer
    ADD CONSTRAINT UQ_Customers_Nickname UNIQUE (chat_id);

-- changeset liquibase:3
CREATE TABLE link
(
    id            INT8 GENERATED ALWAYS AS IDENTITY NOT NULL,
    click_count   INT4 DEFAULT 0,
    token         VARCHAR(32) NOT NULL,
    url           VARCHAR(999) NOT NULL,
    customer      INT8,
    PRIMARY KEY (id)
);

-- changeset liquibase:4
ALTER TABLE link
    ADD CONSTRAINT UQ_Links_ShortenedUrl UNIQUE (token);

-- changeset liquibase:5
ALTER TABLE link
    ADD FOREIGN KEY (customer) REFERENCES customer(id);