/*Создание таблицы candidate для модели Candidate*/
create table if not exists candidate(
candidate_id serial primary key,
name varchar(100),
description text,
photo bytea,
created timestamp
);
