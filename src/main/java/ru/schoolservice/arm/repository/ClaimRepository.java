package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Claim;

@Transactional(readOnly = true)
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    @Query("SELECT DISTINCT c FROM Claim c LEFT JOIN FETCH c.insurances WHERE c.id = :id")
    Claim findClaimWithInsurancesById(@Param("id") Integer id);
}
