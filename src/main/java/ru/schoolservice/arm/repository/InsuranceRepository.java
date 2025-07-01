package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.InsuranceContractDataEntity;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<InsuranceContractDataEntity, Integer> {

    List<InsuranceContractDataEntity> findByClaimId(Integer claimId);

    void deleteAllByClaimId(Integer claimId);
}
