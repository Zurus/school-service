-- Создание таблицы insurance_programs
CREATE TABLE insurance_programs (
                                    id BIGSERIAL PRIMARY KEY,
                                    code VARCHAR(255),
                                    name VARCHAR(255),
                                    report_form_code VARCHAR(255),
                                    dbo_risk_code VARCHAR(255),
                                    abs_program_code VARCHAR(255)
);

-- Вставка данных в таблицу insurance_programs
INSERT INTO insurance_programs (code, name, report_form_code, dbo_risk_code, abs_program_code) VALUES
                                                                                                   ('И1', 'Программа медицинского страхования', 'RF001', 'DR001', 'AP001'),
                                                                                                   ('И2', 'Программа медицинского страхования', 'RF001', 'DR001', 'AP001'),
                                                                                                   ('И3', 'Программа туристического страхования', 'RF005', 'DR005', 'AP005');


CREATE TABLE IF NOT EXISTS insurance_credit_risk (
                                                     id BIGSERIAL PRIMARY KEY,
                                                     code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    object_type VARCHAR(50),
    abs_risk_code VARCHAR(50)
    );
--rollback DROP TABLE IF EXISTS insurance_credit_risk;

INSERT INTO insurance_credit_risk (code, name, object_type, abs_risk_code)
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

CREATE TABLE IF NOT EXISTS insurance_programs_risk (
                                                       id BIGSERIAL PRIMARY KEY,
                                                       insurance_program_id BIGINT NOT NULL REFERENCES INSURANCE_PROGRAMS(id),
    active_from DATE NOT NULL,
    active_to DATE NOT NULL,
    risk_id BIGINT NOT NULL REFERENCES insurance_credit_risk(id),
    non_standart boolean NOT NULL,
    sure boolean NOT NULL
    );
--rollback DROP TABLE IF EXISTS insurance_programs_risk;

CREATE OR REPLACE FUNCTION insert_insurance_program_risk(
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
FROM insurance_programs
WHERE code = p_program_code;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Страховая программа с кодом % не найдена', p_program_code;
END IF;

    -- Получение ID риска по коду
SELECT id INTO v_risk_id
FROM insurance_credit_risk
WHERE code = p_risk_code;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Риск с кодом % не найден', p_risk_code;
END IF;

    -- Вставка записи с ID программы и риска
INSERT INTO insurance_programs_risk (
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
--rollback DROP FUNCTION IF EXISTS insert_insurance_program_risk(VARCHAR, VARCHAR, DATE, DATE);

SELECT insert_insurance_program_risk('И1', 'HEALTHLIFE', false, true);
SELECT insert_insurance_program_risk('И1', 'TELEMEDICINE');
SELECT insert_insurance_program_risk('И1', 'JOBLOSS', true);
SELECT insert_insurance_program_risk('И1', 'CRITICALILLNESS', true);
SELECT insert_insurance_program_risk('И1', 'WORKABILITYLOSS', true);

SELECT insert_insurance_program_risk('И2', 'HEALTHLIFE', false , true);
SELECT insert_insurance_program_risk('И2', 'JOBLOSS');
SELECT insert_insurance_program_risk('И2', 'CRITICALILLNESS');
SELECT insert_insurance_program_risk('И2', 'TELEMEDICINE');
SELECT insert_insurance_program_risk('И2', 'WORKABILITYLOSS',true);

SELECT insert_insurance_program_risk('И3', 'HEALTHLIFE', false, true);
SELECT insert_insurance_program_risk('И3', 'TELEMEDICINE');
SELECT insert_insurance_program_risk('И3', 'WORKABILITYLOSS');
SELECT insert_insurance_program_risk('И3', 'JOBLOSS', true);
SELECT insert_insurance_program_risk('И3', 'CRITICALILLNESS', true);
--rollback DELETE FROM insurance_programs_risk icr WHERE icr.insurance_programs_id IN (SELECT ip.id from insurance_programs ip where ip.code in ('И1', 'И2', 'И3'));

ALTER TABLE IF EXISTS insurance_credit_risk ADD COLUMN IF NOT EXISTS is_combined BOOLEAN NOT NULL DEFAULT FALSE;

INSERT INTO insurance_credit_risk (code, name, object_type, abs_risk_code, is_combined)
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
FROM insurance_credit_risk
WHERE code = insurance_credit_risk_code AND is_combined = TRUE;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Не найден страховой риск "%"', insurance_credit_risk_code;
END IF;

    -- Проверяем существование всех имён из массива
SELECT ARRAY(
           SELECT n
                   FROM unnest(insurance_credit_risk_codes) AS n
                   WHERE NOT EXISTS (
                           SELECT 1 FROM insurance_credit_risk WHERE code = n
                       )
               ) INTO missing_names;

IF array_length(missing_names, 1) > 0 THEN
        RAISE EXCEPTION 'Следующие коды не найдены: %', missing_names;
END IF;

    -- Вставляем связи в my_second_table
INSERT INTO insurance_combo_risk (insurance_combo_credit_risk_id, insurance_combined_credit_risk_id)
SELECT
    insurance_credit_risk_id,
    (SELECT id FROM insurance_credit_risk WHERE code = n)
FROM unnest(insurance_credit_risk_codes) AS n;

END;
$$ LANGUAGE plpgsql;

SELECT insert_insurance_program_risk('И1', 'HEALTHLIFE_WL', false, true);

select fill_combined_insuranceprogram_risk('HEALTHLIFE_WL', ARRAY['HEALTHLIFE', 'WORKABILITYLOSS']);