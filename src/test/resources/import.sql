-- Очистка данных в правильном порядке (с учетом зависимостей)
DELETE
FROM insurance_members;
DELETE
FROM members;
DELETE
FROM insuranceContractDataEntities;
DELETE
FROM claims;

-- Сброс sequence для H2
ALTER TABLE claims
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE insuranceContractDataEntities
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE members
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE insurance_members
    ALTER COLUMN id RESTART WITH 1;

-- Вставка заявок
INSERT INTO claims (name)
VALUES ('Заявка 1');
INSERT INTO claims (name)
VALUES ('Заявка 2');

-- Вставка договоров страхования
# INSERT INTO insuranceContractDataEntities (value, claim_id)
# VALUES ('Договор 1', 1);
# INSERT INTO insuranceContractDataEntities (value, claim_id)
# VALUES ('Договор 2', 1);
# INSERT INTO insuranceContractDataEntities (value, claim_id)
# VALUES ('Договор 3', 2);
# INSERT INTO insuranceContractDataEntities (value, claim_id)
# VALUES ('Договор 4', 2);

# -- Вставка участников
# INSERT INTO members (value)
# VALUES ('Иванов Иван');
# INSERT INTO members (value)
# VALUES ('Петров Петр');
# INSERT INTO members (value)
# VALUES ('Сидорова Мария');
# INSERT INTO members (value)
# VALUES ('Козлов Дмитрий');
#
# -- Создание связей между договорами и участниками
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (1, 1); -- Договор 1 -> Иванов
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (1, 2); -- Договор 1 -> Петров
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (2, 3); -- Договор 2 -> Сидорова
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (3, 4); -- Договор 3 -> Козлов
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (4, 1); -- Договор 4 -> Иванов
# INSERT INTO insurance_members (insurance_id, member_id)
# VALUES (4, 4); -- Договор 4 -> Козлов