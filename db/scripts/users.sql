create table if not exists users(
    user_id serial primary key,
    name varchar(200) constraint name_uneque unique
);
