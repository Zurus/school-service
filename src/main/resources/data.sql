
--BIQ-20227
--changeset Divaev-AM:task_biq-20227_id_1
--comment Создание справочника продуктовых рисков
CREATE TABLE IF NOT EXISTS credit_products_service.insurance_credit_risk (
                                                                             id BIGSERIAL PRIMARY KEY,
                                                                             code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    object_type VARCHAR(50),
    abs_risk_code VARCHAR(50)
    );
--rollback DROP TABLE IF EXISTS credit_products_service.insurance_credit_risk;

--changeset Divaev-AM:task_biq-20227_id_2
--comment Заполняет справочник продуктовых рисков
INSERT INTO credit_products_service.insurance_credit_risk (code, name, object_type, abs_risk_code)
VALUES
    ('HEALTHLIFE', 'Жизнь/Здоровье', 'BORROWER', 'HELTHLIFE'),
    ('TELEMEDICINE', 'Телемедицина', 'BORROWER', 'TELEMEDICINE'),
    ('JOBLOSS', 'Потеря работы', 'BORROWER', 'JOBLOSS'),
    ('WORKABILITYLOSS', 'Временная утрата трудоспособности', 'BORROWER', 'WORKABILITYLOSS'),
    ('CRITICALILLNESS', 'Критическое заболевание', 'BORROWER', 'CRITICALILLNESS'),
    ('PLEDGE', 'Имущество','OBJECT', 'PLEDGE')
    ON CONFLICT (code) DO UPDATE SET
    name = EXCLUDED.name,
                              object_type = EXCLUDED.object_type,
                              abs_risk_code = EXCLUDED.abs_risk_code;
--rollback DELETE FROM insurance_credit_risk WHERE code IN ('HEALTHLIFE', 'TELEMEDICINE', 'JOBLOSS', 'WORKABILITYLOSS', 'CRITICALILLNESS', 'PLEDGE');

-- В схеме credit_products_service
--changeset Divaev-AM:task_biq-20227_id_3
--comment Создает связь рисков и программ страхования
CREATE TABLE IF NOT EXISTS credit_products_service.insurance_programs_risk (
                                                                               id BIGSERIAL PRIMARY KEY,
                                                                               insurance_program_id BIGINT NOT NULL REFERENCES credit_products_service.INSURANCE_PROGRAMS(id),
    active_from DATE NOT NULL,
    active_to DATE NOT NULL,
    risk_id BIGINT NOT NULL REFERENCES credit_products_service.insurance_credit_risk(id),
    non_standart boolean NOT NULL,
    sure boolean NOT NULL
    );
--rollback DROP TABLE IF EXISTS credit_products_service.insurance_programs_risk;

--changeset Divaev-AM:task_biq-20227_id_4
--comment Создает функцию заполнения связи рисков и программ страхования
CREATE OR REPLACE FUNCTION credit_products_service.insert_insurance_program_risk(
    p_program_code VARCHAR(10),        -- Код страховой программы (И1, И2)
    p_risk_code VARCHAR(50),           -- Код риска (JOBLOSS)
    p_non_standart boolean default false,
    p_sure boolean default false, -- Признак обязательности страхового риска
    p_date_from DATE DEFAULT '2025-04-07',  -- Начало периода активности
    p_date_to DATE DEFAULT '2099-01-01'     -- Окончание периода активности
) RETURNS VOID AS $$
DECLARE
v_risk_id BIGINT;
    v_program_id BIGINT;
BEGIN
    -- Проверка корректности периода
    IF p_date_from > p_date_to THEN
        RAISE EXCEPTION 'Дата начала % не может быть позже даты окончания %', p_date_from, p_date_to;
END IF;

    -- Получение ID страховой программы по коду
SELECT id INTO v_program_id
FROM credit_products_service.insurance_programs
WHERE code = p_program_code;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Страховая программа с кодом % не найдена', p_program_code;
END IF;

    -- Получение ID риска по коду
SELECT id INTO v_risk_id
FROM credit_products_service.insurance_credit_risk
WHERE code = p_risk_code;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Риск с кодом % не найден', p_risk_code;
END IF;

    -- Вставка записи с ID программы и риска
INSERT INTO credit_products_service.insurance_programs_risk (
    insurance_program_id,
    active_from,
    active_to,
    risk_id,
    non_standart,
    sure
) VALUES (
             v_program_id,   -- Используем ID программы вместо кода
             p_date_from,
             p_date_to,
             v_risk_id,
             p_non_standart,
             p_sure
         );
END;
$$ LANGUAGE plpgsql;
--rollback DROP FUNCTION IF EXISTS credit_products_service.insert_insurance_program_risk(VARCHAR, VARCHAR, DATE, DATE);

--changeset Divaev-AM:task_biq-20227_id_5
--comment Создает функцию заполнения связи рисков и программ страхования
SELECT credit_products_service.insert_insurance_program_risk('И1', 'HEALTHLIFE', false, true);
SELECT credit_products_service.insert_insurance_program_risk('И1', 'TELEMEDICINE');
SELECT credit_products_service.insert_insurance_program_risk('И1', 'JOBLOSS', true);
SELECT credit_products_service.insert_insurance_program_risk('И1', 'CRITICALILLNESS', true);
SELECT credit_products_service.insert_insurance_program_risk('И1', 'WORKABILITYLOSS', true);

SELECT credit_products_service.insert_insurance_program_risk('И2', 'HEALTHLIFE', false , true);
SELECT credit_products_service.insert_insurance_program_risk('И2', 'JOBLOSS');
SELECT credit_products_service.insert_insurance_program_risk('И2', 'CRITICALILLNESS');
SELECT credit_products_service.insert_insurance_program_risk('И2', 'TELEMEDICINE');
SELECT credit_products_service.insert_insurance_program_risk('И2', 'WORKABILITYLOSS',true);

SELECT credit_products_service.insert_insurance_program_risk('И3', 'HEALTHLIFE', false, true);
SELECT credit_products_service.insert_insurance_program_risk('И3', 'TELEMEDICINE');
SELECT credit_products_service.insert_insurance_program_risk('И3', 'WORKABILITYLOSS');
SELECT credit_products_service.insert_insurance_program_risk('И3', 'JOBLOSS', true);
SELECT credit_products_service.insert_insurance_program_risk('И3', 'CRITICALILLNESS', true);
--rollback DELETE FROM credit_products_service.insurance_programs_risk icr WHERE icr.insurance_programs_id IN (SELECT ip.id from insurance_programs ip where ip.code in ('И1', 'И2', 'И3'));

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, Телемедицина (№1)  (колл.)' WHERE code = 'РСХБ1';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, телемедицина (№1)  (колл.)' WHERE code = 'РСХБ1';

UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни и здоровья, Телемедицина (№1)  (колл.)' WHERE code = 'РСХБ1-СКФО';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни и здоровья, телемедицина (№1).  (колл.)' WHERE code = 'РСХБ1-СКФО';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, критического заболевания, потери работы, Телемедицина (№2) (колл.)' WHERE code = 'РСХБ2';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, ПР, КритЗаб, Телемедицина (№2) (колл)' WHERE code = 'РСХБ2';

UPDATE credit_products_service.insurance_programs SET name = 'СКФО.Страхование жизни, здоровья, критического заболевания, потери работы, Телемедицина (№2)(колл.)' WHERE code = 'РСХБ2-СКФО';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни, здоровья, ПР, КритЗаб, Телемедицина (№2) (колл)' WHERE code = 'РСХБ2-СКФО';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, Телемедицина (№5)  (колл.)' WHERE code = 'РСХБ5';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, телемедицина (№5) (колл.)' WHERE code = 'РСХБ5';

UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни и здоровья, Телемедицина (№5)  (колл.)' WHERE code = 'РСХБ5-СКФО';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни, телемедицина (№5). (колл.)' WHERE code = 'РСХБ5-СКФО';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, Телемедицина (№7)  (колл.)' WHERE code = 'РСХБ7';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, НС, телемедицина (№7) (колл.)' WHERE code = 'РСХБ7';

UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни и здоровья, Телемедицина (№7)  (колл.)' WHERE code = 'РСХБ7-СКФО';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни, здоровья, НС, телемедицина (№7) (колл.)' WHERE code = 'РСХБ7-СКФО';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, временной утраты трудоспособности, Телемедицина (№9) (колл.)' WHERE code = 'РСХБ9';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, НС, Телемедицина (№9) (колл.)' WHERE code = 'РСХБ9';

UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни, здоровья, временной утраты трудоспособности, Телемедицина (№9) (колл.)' WHERE code = 'РСХБ9-СКФО';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'СКФО. Страхование жизни, здоровья, НС, Телемедицина (№9) (колл.)' WHERE code = 'РСХБ9-СКФО';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, Телемедицина (инд.)' WHERE code = 'И1';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья (инд.)' WHERE code = 'И1';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, критического заболевания, потери работы, Телемедицина (инд.)' WHERE code = 'И2';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, ПР, КритЗаб, Телемедицина (инд)' WHERE code = 'И2';

UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни и здоровья, временной утраты трудоспособности, Телемедицина (инд.)' WHERE code = 'И3';
--rollback UPDATE credit_products_service.insurance_programs SET name = 'Страхование жизни, здоровья, НС, Телемедицина (инд)' WHERE code = 'И3';

ALTER TABLE IF EXISTS credit_products_service.insurance_credit_risk ADD COLUMN IF NOT EXISTS is_combined BOOLEAN NOT NULL DEFAULT FALSE;

INSERT INTO credit_products_service.insurance_credit_risk (code, name, object_type, abs_risk_code, is_combined)
VALUES
    ('HEALTHLIFE_WL', 'Комбинированный  риск: Жизнь/Здоровье + Временная утрата трудоспособности','BORROWER','HEALTHLIFE_WL', true);

CREATE TABLE insurance_combo_risk (
                                      id SERIAL PRIMARY KEY,
                                      insurance_combo_credit_risk_id INTEGER REFERENCES insurance_credit_risk(id),
                                      insurance_combined_credit_risk_id INTEGER REFERENCES insurance_credit_risk(id)
);


CREATE OR REPLACE FUNCTION fill_combined_insuranceprogram_risk(
    insurance_credit_risk_code VARCHAR(255),
    insurance_credit_risk_codes VARCHAR(255)[]
) RETURNS VOID AS $$
DECLARE
insurance_credit_risk_id INTEGER;
    missing_names VARCHAR(255)[];
BEGIN
    -- Проверяем существование комбо-записи и получаем её ID
SELECT id INTO insurance_credit_risk_id
FROM credit_products_service.insurance_credit_risk
WHERE code = insurance_credit_risk_code AND is_combined = TRUE;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Не найден страховой риск "%"', insurance_credit_risk_code;
END IF;

    -- Проверяем существование всех имён из массива
SELECT ARRAY(
           SELECT n
                   FROM unnest(insurance_credit_risk_codes) AS n
                   WHERE NOT EXISTS (
                           SELECT 1 FROM credit_products_service.insurance_credit_risk WHERE code = n
                       )
               ) INTO missing_names;

IF array_length(missing_names, 1) > 0 THEN
        RAISE EXCEPTION 'Следующие коды не найдены: %', missing_names;
END IF;

    -- Вставляем связи в my_second_table
INSERT INTO credit_products_service.insurance_combo_risk (id_insurance_combo_credit_risk, id_insurance_combined_credit_risk)
SELECT
    insurance_credit_risk_id,
    (SELECT id FROM credit_products_service.insurance_credit_risk WHERE code = n)
FROM unnest(insurance_credit_risk_codes) AS n;

END;
$$ LANGUAGE plpgsql;

SELECT credit_products_service.insert_insurance_program_risk('И1', 'HEALTHLIFE_WL', false, true);

select credit_products_service.fill_combined_insuranceprogram_risk('HEALTHLIFE_WL', ARRAY['HEALTHLIFE', 'WORKABILITYLOSS']);