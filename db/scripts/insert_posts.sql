/*Наполнение таблицы post*/
insert into post(name, visible, description, city_id, created)
values ('Junior Java Job', true, 'salary 1000$', 1, '05.04.2022 16:44');
insert into post(name, visible, description, city_id, created)
values ('Middle Java Job', false, 'salary 2000$', 2, '03.04.2022 01:36');
insert into post(name, visible, description, city_id, created)
values ('Senior Java Job', false, 'salary 4000$', 3, '01.04.2022 11:00');
ALTER SEQUENCE serial RESTART WITH 1;