/*Создание таблицы post для модели Post*/
create table if not exists post(
post_id serial primary key,
name VARCHAR(100),
visible boolean,
description text,
city_id int references city (city_id),
created timestamp
);