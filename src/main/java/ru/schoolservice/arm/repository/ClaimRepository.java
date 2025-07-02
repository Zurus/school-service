package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.schoolservice.arm.model.InsuranceContractOperClaims;


@Repository
public interface ClaimRepository extends JpaRepository<InsuranceContractOperClaims, Integer> {

    @Query("SELECT DISTINCT c FROM InsuranceContractOperClaims c " +
            "LEFT JOIN FETCH c.insuranceContractDataEntities i " + // имя поля изменено на insurances
            "LEFT JOIN FETCH i.risks r " + // имя поля изменено на insurances
            "WHERE c.id = :id")
    InsuranceContractOperClaims findClaimWithInsurancesById(@Param("id") Integer id);
}
