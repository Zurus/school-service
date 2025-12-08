package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.schoolservice.arm.model.CheckPassportEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckPassportRepository extends JpaRepository<CheckPassportEntity, Long> {
    // Метод, который вызывает NonUniqueResultException при дублировании
    CheckPassportEntity findByFioAndBirthDateAndDocSeriesAndDocNumber(
            String fio,
            LocalDate birthDate,
            String docSeries,
            String docNumber
    );

    // Альтернативный метод для получения всех записей
    List<CheckPassportEntity> findAllByFioAndBirthDateAndDocSeriesAndDocNumber(
            String fio,
            LocalDate birthDate,
            String docSeries,
            String docNumber
    );
}
