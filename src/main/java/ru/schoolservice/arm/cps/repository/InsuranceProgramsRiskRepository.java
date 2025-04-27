package ru.schoolservice.arm.cps.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;

import java.time.LocalDate;
import java.util.List;

public interface InsuranceProgramsRiskRepository {

    @Query("SELECT r FROM InsuranceProgramsRiskEntity r "
            + "LEFT JOIN FETCH r.program "
            + "LEFT JOIN FETCH r.risk "
            + "WHERE r.program.code = :code "
            + "AND (:date BETWEEN r.activeFrom AND r.activeTo)")
    List<InsuranceProgramsRiskEntity> findActiveRisksByProgramCodeAndDate(
            @Param("code") String programCode,
            @Param("date") LocalDate date);
}
