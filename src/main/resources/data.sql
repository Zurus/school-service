INSERT INTO USERS (EMAIL, NAME, PASSWORD, PHONE_NUMBER, SCHOOL_ID, CARD_ID, IS_EMLOYEE, CLASS_NUMBER, JOB_TITLE,
                   CLASS_ROOM_TEACHER, TELEGRAM)
VALUES ('user@gmail.com', 'User', '{noop}password', '+79026165214', '34543-101', '056,35766', false, '1A', null, null,
        'asdf'),
       ('admin@javaops.ru', 'Admin', '{noop}admin', '+79872832442', '34543-101', '251,35066', true, null, 'Трудовик',
        false, null),
       ('sigur@javaops.ru', 'sigur_AI', '{noop}sigur', '+11111111111', '00000-000', '000000-00', true, '', '', null,
        null);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('SIGUR', 3);