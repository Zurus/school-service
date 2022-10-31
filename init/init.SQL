-- 28.10.2022

alter table if exists classes
    drop constraint if exists classes_classroom_teacher_id_fk;

alter table if exists classes
    drop constraint if exists classes_school_id_fk;

alter table if exists classroom_teachers
    drop constraint if exists classroom_teachers_class_id_fk;

alter table if exists classroom_teachers
    drop constraint if exists classroom_teachers_user_id_fk;

alter table if exists event
    drop constraint if exists events_user_id_fk;

alter table if exists user_messages
    drop constraint if exists user_messages_message_id_fk;

alter table if exists user_messages
    drop constraint if exists user_messages_user_id_fk;

alter table if exists user_role
    drop constraint if exists user_role_user_id_fk;

alter table if exists users
    drop constraint if exists users_class_id_fk;

drop table if exists classes cascade;

drop table if exists classroom_teachers cascade;

drop table if exists event cascade;

drop table if exists message cascade;

drop table if exists schools cascade;

drop table if exists user_messages cascade;

drop table if exists user_role cascade;

drop table if exists users cascade;

create table classes
(
    id                   serial       not null,
    name                 varchar(128) not null,
    classroom_teacher_id int4,
    school_id            varchar(255) not null,
    primary key (id)
);

create table classroom_teachers
(
    id       serial not null,
    class_id int4,
    user_id  int4,
    primary key (id)
);

create table event
(
    id            serial not null,
    event_type    int4,
    event_time    timestamp,
    log_id        int4,
    user_key_card varchar(255),
    user_id       int4   not null,
    primary key (id)
);

create table message
(
    id           serial       not null,
    message_type int4,
    text         varchar(255) not null,
    primary key (id)
);

create table schools
(
    id   varchar(32) not null,
    name varchar(64) not null,
    primary key (id)
);

create table user_messages
(
    user_id    int4 not null,
    message_id int4 not null,
    primary key (user_id, message_id)
);

create table user_role
(
    user_id int4 not null,
    role    varchar(255)
);

create table users
(
    id           serial       not null,
    name         varchar(128) not null,
    card_id      varchar(255) not null,
    email        varchar(128) not null,
    password     varchar(256),
    phone_number varchar(255) not null,
    photo        bytea,
    telegram     varchar(255),
    class_id     int4         not null,
    primary key (id)
);

alter table if exists user_role
    add constraint uk_user_role unique (user_id, role);

alter table if exists users
    add constraint users_email_uk unique (email);

alter table if exists classes
    add constraint classes_classroom_teacher_id_fk
        foreign key (classroom_teacher_id)
            references classroom_teachers;

alter table if exists classes
    add constraint classes_school_id_fk
        foreign key (school_id)
            references schools;

alter table if exists classroom_teachers
    add constraint classroom_teachers_class_id_fk
        foreign key (class_id)
            references classes;

alter table if exists classroom_teachers
    add constraint classroom_teachers_user_id_fk
        foreign key (user_id)
            references users;

alter table if exists event
    add constraint events_user_id_fk
        foreign key (user_id)
            references users;

alter table if exists user_messages
    add constraint user_messages_message_id_fk
        foreign key (message_id)
            references message;

alter table if exists user_messages
    add constraint user_messages_user_id_fk
        foreign key (user_id)
            references users;

alter table if exists user_role
    add constraint user_role_user_id_fk
        foreign key (user_id)
            references users
            on delete cascade;

alter table if exists users
    add constraint users_class_id_fk
        foreign key (class_id)
            references classes;

-- 31.10.2022 TestData to webDel

INSERT INTO SCHOOLS (ID, NAME)
VALUES ('A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6', 'Аксаковская гимназия №11'),
       ('A1B2C3D4E5F6A7B8C9D0E1F2A3B41234','Школа святого петра');

INSERT INTO CLASSES (NAME, SCHOOL_ID)
VALUES ('1A', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6'),
       ('2B', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B41234');

INSERT INTO USERS (EMAIL, NAME, PASSWORD, PHONE_NUMBER, CARD_ID, TELEGRAM, CLASS_ID)
VALUES ('user@gmail.com', 'User', '{noop}password', '+79026165214', '388BB6', 'asdf', 1),
       ('admin@javaops.ru', 'Admin', '{noop}admin', '+79872832442', 'FB88FA', null, 1),
       ('sigur@javaops.ru', 'sigur_AI', '{noop}sigur', '+11111111111', '6F3039', null, 1),
       ('new_user@javaops.ru', 'simple_AI', '{noop}simple', '+11111111111', '5E5C79', null, 2);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('SIGUR', 3);
