create table if not exists users
(
    user_id  serial primary key,
    email    varchar(200)
        constraint email_unique unique,
    password varchar(20)
);
