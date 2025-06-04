package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.Risks;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface RisksRepository extends JpaRepository<Risks, Integer> {

    //List<Risks> findByInsuranceIn(Integer insuranceProgramId);
}
