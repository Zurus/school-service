package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.repository.ClaimRepository;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private ClaimRepository claimRepository;

    public Claim getClaimWithInsuranceById(Integer id) {
        return claimRepository.findClaimWithInsurancesById(id);
    }

    public Claim getClaimById(Integer id) {
        Claim claim = claimRepository.findById(id).get();
        return claim;
    }

    @Transactional
    public Claim getClaimByIdSave(Integer id) {
        return claimRepository.getById(id);
    }
}
