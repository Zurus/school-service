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