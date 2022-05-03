-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE customer
(
    id        bigserial NOT NULL,
    nick_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- changeset liquibase:2
ALTER TABLE customer
    ADD CONSTRAINT UQ_Customers_Nickname UNIQUE (nick_name);

-- changeset liquibase:3
CREATE TABLE link
(
    id            bigserial NOT NULL,
    click_count   INT4 DEFAULT 0,
    shortened_url VARCHAR(64) NOT NULL,
    url           VARCHAR(999) NOT NULL,
    customer      INT8 NOT NULL,
    PRIMARY KEY (id)
);

-- changeset liquibase:4
ALTER TABLE link
    ADD CONSTRAINT UQ_Links_ShortenedUrl UNIQUE (shortened_url);

-- changeset liquibase:5
ALTER TABLE link
    ADD FOREIGN KEY (customer) REFERENCES customer;
