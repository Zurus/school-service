-- Создание таблицы insurance_programs
CREATE TABLE insurance_programs (
                                    id BIGSERIAL PRIMARY KEY,
                                    code VARCHAR(255),
                                    name VARCHAR(255),
                                    report_form_code VARCHAR(255),
                                    dbo_risk_code VARCHAR(255),
                                    abs_program_code VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS insurance_credit_risk (
                                                     id BIGSERIAL PRIMARY KEY,
                                                     code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    object_type VARCHAR(50),
    abs_risk_code VARCHAR(50)
    );
--rollback DROP TABLE IF EXISTS insurance_credit_risk;

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

ALTER TABLE IF EXISTS insurance_credit_risk ADD COLUMN IF NOT EXISTS is_combined BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE insurance_combo_risk (
                                      id BIGSERIAL PRIMARY KEY,
                                      insurance_combo_credit_risk_id INTEGER REFERENCES insurance_credit_risk(id),
                                      insurance_combined_credit_risk_id INTEGER REFERENCES insurance_credit_risk(id)
);





select fill_combined_insuranceprogram_risk('HEALTHLIFE_WL', ARRAY['HEALTHLIFE', 'WORKABILITYLOSS']);