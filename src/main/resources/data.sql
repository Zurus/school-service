-- Удаление таблиц
DROP TABLE IF EXISTS check_passport_processes;
DROP TABLE IF EXISTS check_passport;

-- Создание таблицы check_passport
CREATE TABLE check_passport (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                fio VARCHAR(255),
                                birth_date DATE,
                                doc_series VARCHAR(10),
                                doc_number VARCHAR(20),
                                check_status VARCHAR(50),
                                actualization_date TIMESTAMP,
                                load_date TIMESTAMP
);

-- Создание таблицы check_passport_processes
CREATE TABLE check_passport_processes (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          check_passport_id BIGINT,
                                          process_id VARCHAR(255),
                                          FOREIGN KEY (check_passport_id) REFERENCES check_passport(id)
);


-- Вставка ДВУХ одинаковых записей в check_passport (для воспроизведения ошибки)
-- INSERT INTO check_passport (fio, birth_date, doc_series, doc_number, check_status, actualization_date, load_date)
-- VALUES ('Иванов Иван Иванович', '1990-01-01', '1234', '567890', 'ACTUAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
-- INSERT INTO check_passport (fio, birth_date, doc_series, doc_number, check_status, actualization_date, load_date)
-- VALUES ('Иванов Иван Иванович', '1990-01-01', '1234', '567890', 'ACTUAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);