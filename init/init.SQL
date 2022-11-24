alter table if exists classes
    drop constraint if exists classes_user_id_fk;


alter table if exists classes
    drop constraint if exists classes_school_id_fk;


alter table if exists events
    drop constraint if exists events_card_id_fk;


alter table if exists notification_contact
    drop constraint if exists notification_contact_contact_id;

alter table if exists notification_contact
    drop constraint if exists notification_contact_notification_id_fk;


alter table if exists user_contacts
    drop constraint if exists user_contacts_contact_id_fk;


alter table if exists user_contacts
    drop constraint if exists user_contacts_user_id_fk;


alter table if exists user_role
    drop constraint if exists user_role_user_id_fk;


alter table if exists users
    drop constraint if exists user_card_id_fk;

alter table if exists users
    drop constraint if exists user_classes_id_fk;

drop table if exists cards cascade;

drop table if exists classes cascade;

drop table if exists contacts cascade;

drop table if exists events cascade;

drop table if exists notification_contact cascade;

drop table if exists notifications cascade;

drop table if exists schools cascade;

drop table if exists settings cascade;

drop table if exists unknown_events cascade;

drop table if exists user_contacts cascade;

drop table if exists user_role cascade;

drop table if exists users cascade;

create table cards
(
    id            serial       not null,
    card_hex_code varchar(255) not null,
    primary key (id)
);

create table classes
(
    id                   serial       not null,
    name                 varchar(128) not null,
    classroom_teacher_id int4,
    school_id            varchar(255) not null,
    primary key (id)
);

create table contacts
(
    id           serial       not null,
    chat_id      varchar(255),
    phone_number varchar(255) not null,
    telegram     varchar(255),
    primary key (id)
);

create table events
(
    id         serial not null,
    event_type int4,
    event_time timestamp,
    log_id     int4,
    key_hex    varchar(255),
    card_id    int4   not null,
    primary key (id)
);

create table notification_contact
(
    notification_id int4 not null,
    contact_id      int4 not null
);

create table notifications
(
    id                serial       not null,
    notification_type varchar(255) not null,
    text              varchar(255) not null,
    primary key (id)
);

create table schools
(
    id   varchar(32) not null,
    name varchar(64) not null,
    primary key (id)
);

create table settings
(
    id    serial       not null,
    key   varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
);

create table unknown_events
(
    id         serial       not null,
    event_type int4,
    event_time timestamp,
    log_id     int4,
    card       varchar(255) not null,
    primary key (id)
);

create table user_contacts
(
    user_id    int4 not null,
    contact_id int4 not null
);

create table user_role
(
    user_id int4 not null,
    role    varchar(255)
);

create table users
(
    id                 serial       not null,
    name               varchar(128) not null,
    email              varchar(128) not null,
    password           varchar(256),
    photo              bytea,
    with_notifications boolean      not null,
    card_id            int4         not null,
    class_id           int4         not null,
    primary key (id)
);

alter table if exists events
    add constraint event_card_id_log_id_uk unique (card_id, log_id);

alter table if exists notification_contact
    add constraint notification_contact_uk unique (notification_id, contact_id);

alter table if exists user_contacts
    add constraint user_contact_uk unique (user_id, contact_id);

alter table if exists user_role
    add constraint user_role_uk unique (user_id, role);

alter table if exists users
    add constraint users_email_uk unique (email);

alter table if exists classes
    add constraint classes_user_id_fk
        foreign key (classroom_teacher_id)
            references users;

alter table if exists classes
    add constraint classes_school_id_fk
        foreign key (school_id)
            references schools;

alter table if exists events
    add constraint events_card_id_fk
        foreign key (card_id)
            references cards;

alter table if exists notification_contact
    add constraint notification_contact_contact_id
        foreign key (contact_id)
            references contacts;

alter table if exists notification_contact
    add constraint notification_contact_notification_id_fk
        foreign key (notification_id)
            references notifications;

alter table if exists user_contacts
    add constraint user_contacts_contact_id_fk
        foreign key (contact_id)
            references contacts;

alter table if exists user_contacts
    add constraint user_contacts_user_id_fk
        foreign key (user_id)
            references users;

alter table if exists user_role
    add constraint user_role_user_id_fk
        foreign key (user_id)
            references users
            on
                delete
                cascade;

alter table if exists users
    add constraint user_card_id_fk
        foreign key (card_id)
            references cards;

alter table if exists users
    add constraint user_classes_id_fk
        foreign key (class_id)
            references classes;


INSERT INTO SETTINGS(KEY, VALUE)
VALUES ('bot.name', 'SecureEnvBot'),
       ('bot.token', '5596926556:AAFHZqdaMFrDZ1Dm7fxw404RodHM3p01dzc'),
       ('test.key', 'test.value');

INSERT INTO CARDS (ID, CARD_HEX_CODE)
VALUES (1, '388BB6'),
       (2, 'FB88FA'),
       (3, '6F3039'),
       (4, '388BB5'),
       (5, '9C8BB1'),
--        Тестирование
       (6, '9CE51B'),
       (7, 'DACE0B'),
       (8, 'F8C97C');

INSERT INTO CONTACTS (PHONE_NUMBER, TELEGRAM)
VALUES ('+79026165214', 'am_div'),
       ('+79872832442', null),
       ('+11111111111', null),
       ('+79174816110', null),
       ('+79174816110', null),
       ('+79174816110', null),
       ('+79174181965', null);

INSERT INTO SCHOOLS (ID, NAME)
VALUES ('A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6', 'Аксаковская гимназия №11'),
       ('A1B2C3D4E5F6A7B8C9D0E1F2A3B41234', 'Школа святого петра');

INSERT INTO CLASSES (NAME, SCHOOL_ID)
VALUES ('1A', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6'),
       ('2B', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B41234'),
       ('Педработники', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B41234'),
       ('Техперсонал', 'A1B2C3D4E5F6A7B8C9D0E1F2A3B41234');

INSERT INTO USERS (EMAIL, NAME, PASSWORD, CARD_ID, CLASS_ID, WITH_NOTIFICATIONS)
VALUES ('user@gmail.com', 'User', '{noop}password', 1, 1, true),
       ('admin@javaops.ru', 'Admin', '{noop}admin', 2, 1, true),
       ('sigur@javaops.ru', 'sigur_AI', '{noop}sigur', 3, 1, true),
       ('super_teacher@mail.ru', 'math_teacher', '{noop}password', 6, 3, true),
       ('super_cleaner@mail.ru', 'cleaner', '{noop}password', 7, 4, true),
       ('fedua@mail.ru', 'fedua', '{noop}pass', 8, 2, true),
       ('new_user@javaops.ru', 'simple_AI', '{noop}simple', 4, 2, true);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('SIGUR', 3),
       ('USER', 4);

INSERT INTO USER_CONTACTS (USER_ID, CONTACT_ID)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7);

INSERT INTO EVENTS (ID, LOG_ID, EVENT_TYPE, EVENT_TIME, CARD_ID)
VALUES (1, 100170, 2, '2022-10-10', 1),
       (2, 100171, 2, '2021-10-10', 1),
       (3, 100172, 2, '2019-10-10', 1),
       (4, 100173, 2, '2022-09-10', 1),
       (5, 100174, 2, '2019-09-09', 1),
       (6, 100175, 2, '2019-10-09', 1);

delete  from events where 1 =1 ;