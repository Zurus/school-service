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
       ('new_user@javaops.ru', 'simple_AI', '{noop}simple', '+11111111111', '388BB5', null, 2);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('SIGUR', 3);