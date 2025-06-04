package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.model.Risks;
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


    public Claim getClaimWithInsuranceById(Integer id) {
        return claimRepository.findClaimWithInsurancesById(id);
    }

    public Claim getClaimById(Integer id) {
        Claim claim = claimRepository.findById(id).get();
        return claim;
    }


    public List<Member> getMembers(Integer claimId) {
        return memberRepository.findMembersByClaimId(claimId);
    }

    @Transactional
    public Claim getClaimByIdSave(Integer id) {
        return claimRepository.getById(id);
    }

    public List<Insurance> getInsurancesByClaimId(Integer claimId) {
        return insuranceRepository.findByClaimId(claimId);
    }


    public List<Risks> getRisksForInsurance(Integer insuranceId) {
        return null;
        //return risksRepository.findByInsuranceProgramId(insuranceId);
    }

    public void save(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    public void save(List<Risks> risks) {
        risksRepository.saveAll(risks);
    }
}
