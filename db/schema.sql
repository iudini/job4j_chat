create table role (
    id bigserial primary key,
    name varchar(50) not null unique
);

create table person (
    id bigserial primary key,
    login varchar(255) not null unique,
    password varchar(255) not null
);

create table person_roles (
  person_id bigint not null references person,
  roles_id bigint not null references role
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

INSERT INTO role (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
INSERT INTO person (login, password) VALUES ('admin', '$2a$12$GtG4nBHHu4EVQ2Fl0qYEJuQGF1j9NC3rdhiQy.0bFpHkM37VVwjSm');
INSERT INTO person_roles (person_id, roles_id) VALUES (1, 1), (1, 2);
