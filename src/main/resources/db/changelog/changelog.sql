-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id))
-- create table customer (id  bigserial not null, email varchar(255), nick_name varchar(255), password varchar(255), primary key (id));
-- create table link (id  bigserial not null, click_count int4, shortened_url varchar(255), url varchar(255), customer int8, primary key (id));
-- alter table link add constraint FKke6mwicapjcvu66x91n5d12gb foreign key (customer) references customer;