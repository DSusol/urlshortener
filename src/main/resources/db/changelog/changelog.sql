-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE customer
(
    id        bigserial NOT NULL,
    nick_name VARCHAR(255),
    PRIMARY KEY (id)
);

-- changeset liquibase:2
ALTER TABLE customer
    ADD CONSTRAINT nickNameConstrain UNIQUE (nick_name);

-- changeset liquibase:3
CREATE TABLE link
(
    id            bigserial NOT NULL,
    click_count   INT4,
    shortened_url VARCHAR(255),
    url           VARCHAR(255),
    customer      INT8,
    PRIMARY KEY (id)
);

-- changeset liquibase:4
ALTER TABLE link
    ADD CONSTRAINT shortenedUrlConstrain UNIQUE (shortened_url);

-- changeset liquibase:5
ALTER TABLE link
    ADD FOREIGN KEY (customer) REFERENCES customer;
