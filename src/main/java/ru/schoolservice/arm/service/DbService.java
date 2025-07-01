package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.InsuranceContractOperClaims;
import ru.schoolservice.arm.model.InsuranceContractDataEntity;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.model.InsuranceDetailsDataEntity;
import ru.schoolservice.arm.repository.ClaimRepository;
import ru.schoolservice.arm.repository.InsuranceRepository;
import ru.schoolservice.arm.repository.MemberRepository;
import ru.schoolservice.arm.repository.RisksRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private ClaimRepository claimRepository;
    private MemberRepository memberRepository;
    private InsuranceRepository insuranceRepository;
    private RisksRepository risksRepository;


    public InsuranceContractOperClaims getClaimWithInsuranceById(Integer id) {
        return claimRepository.findClaimWithInsurancesById(id);
    }

    public List<Member> getMembers(Integer claimId) {
        return memberRepository.findMembersByClaimId(claimId);
    }

    public List<InsuranceContractDataEntity> getInsurancesByClaimId(Integer claimId) {
        return insuranceRepository.findByClaimId(claimId);
    }
    @Transactional
    public void save(
            InsuranceContractOperClaims insuranceContractOperClaims,
            List<InsuranceDetailsDataEntity> insuranceDetailsDataEntity
    ) {
        // Проблемная строка - удаление данных перед сохранением основной сущности
        insuranceRepository.deleteAllByClaimId(insuranceContractOperClaims.getId());
        claimRepository.saveAndFlush(insuranceContractOperClaims);
        risksRepository.saveAll(insuranceDetailsDataEntity);
    }
}
