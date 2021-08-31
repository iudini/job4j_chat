create table role (
    id bigserial primary key,
    name varchar(50) not null unique
);

create table person (
    id bigserial primary key,
    login varchar(255) not null unique,
    password varchar(255) not null
);

create table room (
    id bigserial primary key,
    name varchar(255) not null unique
);

create table message (
    id bigserial primary key,
    content text,
    created timestamp without time zone default now(),
    person_id bigserial references person(id),
    room_id bigserial references room(id)
);
