/*Создание таблицы city для справочника City*/
create table if not exists city(
    city_id serial primary key,
    name varchar(50)
);