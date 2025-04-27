package ru.schoolservice.arm.cps.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.schoolservice.arm.cps.entity.InsuranceProgramsRiskEntity;
import ru.schoolservice.arm.cps.mapper.InsuranceProgramsRiskMapper;
import ru.schoolservice.arm.cps.model.InsuranceProgramRiskObject;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceProgramsRiskDelegate {

    private final InsuranceProgramRiskService insuranceProgramRiskService;
    private final InsuranceProgramsRiskMapper insuranceProgramsRiskMapper;


    public InsuranceProgramsRiskDelegate(InsuranceProgramRiskService insuranceProgramRiskService, InsuranceProgramsRiskMapper insuranceProgramsRiskMapper) {
        this.insuranceProgramRiskService = insuranceProgramRiskService;
        this.insuranceProgramsRiskMapper = insuranceProgramsRiskMapper;
    }

    public ResponseEntity<List<InsuranceProgramRiskObject>> findRisksByInsuranceCode(String code, Boolean onlyActive) throws Exception {
        return ResponseEntity.ok(buildResponse(insuranceProgramRiskService.findOnlyActiveByInsuranceProgramCode(code)));
    }

    private List<InsuranceProgramRiskObject> buildResponse(List<InsuranceProgramsRiskEntity> listRisk) {
        return listRisk
                .stream()
                .map(insuranceProgramsRiskMapper::toDto)
                .collect(Collectors.toList());
    }
}
