package ru.schoolservice.arm.cps.service;

import org.springframework.stereotype.Service;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;
import ru.schoolservice.arm.cps.repository.InsuranceProgramsRiskRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class InsuranceProgramRiskService {
    private final InsuranceProgramsRiskRepository repository;

    /**
     * Конструктор
     *
     * @param repository репозиторий
     */
    public InsuranceProgramRiskService(InsuranceProgramsRiskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<InsuranceProgramsRiskEntity> findOnlyActiveByInsuranceProgramCode(String insuranceProgramCode) {
        return repository.findActiveRisksByProgramCodeAndDate(insuranceProgramCode, LocalDate.now());
    }
}
