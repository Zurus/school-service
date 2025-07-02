DELETE FROM members;
DELETE FROM insurances;
DELETE FROM claims;

-- Сброс sequence для H2
ALTER TABLE claims ALTER COLUMN id RESTART WITH 1;
ALTER TABLE insurances ALTER COLUMN id RESTART WITH 1;
ALTER TABLE members ALTER COLUMN id RESTART WITH 1;

-- Вставка заявок
INSERT INTO claims (name) VALUES ('Заявка 1');

-- Вставка договоров страхования (каждая вставка в одной строке)
-- INSERT INTO insurances (value, claim_id) VALUES ('Договор 1', 1);
INSERT INTO insurances (value, claim_id) VALUES ('Договор 2', 1);

-- Вставка участников (каждая вставка в одной строке)
INSERT INTO members (value, claim_id) VALUES ('Иванов Иван', 1);
INSERT INTO members (value, claim_id) VALUES ('Петров Петр', 1);
INSERT INTO members (value, claim_id) VALUES ('Сидорова Мария', 1);
INSERT INTO members (value, claim_id) VALUES ('Козлов Дмитрий', 1);