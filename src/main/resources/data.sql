INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', 'password'),
       ('admin@javaops.ru', 'Admin_First', 'Admin_Last', 'admin'),
       ('user0@mail.ru', 'User_Second', 'user_mafaka', 'pass');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('ROLE_USER', 1),
       ('ROLE_ADMIN', 2),
       ('ROLE_USER', 2),
       ('ROLE_USER', 3);