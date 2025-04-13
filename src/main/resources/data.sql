CREATE TABLE insurance_data (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                risk_code VARCHAR(255),
                                abs_risk_code VARCHAR(255),
                                insurance_rate DECIMAL(19, 4),
                                insurance_fee DECIMAL(19, 4),
                                insurance_data_id BIGINT NOT NULL,
                                FOREIGN KEY (insurance_data_id) REFERENCES insurance_data_entity(id) ON DELETE CASCADE
);
;