package ru.schoolservice.arm.cps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InsuranceProgramsRiskRepository extends JpaRepository<InsuranceProgramsRiskEntity, Long> {

    @Query("SELECT DISTINCT r FROM InsuranceProgramsRiskEntity r "
            + "LEFT JOIN FETCH r.program "
            + "LEFT JOIN FETCH r.risk risk "
            + "LEFT JOIN FETCH risk.comboRiskRelations comboRiskRelation "
            + "LEFT JOIN FETCH comboRiskRelation.combinedRisk "
            + "WHERE r.program.code = :code "
            + "AND (:date BETWEEN r.activeFrom AND r.activeTo)")
    List<InsuranceProgramsRiskEntity> findActiveRisksByProgramCodeAndDate(
            @Param("code") String programCode,
            @Param("date") LocalDate date);
}
