package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.InsuranceMembers;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.repository.ClaimRepository;
import ru.schoolservice.arm.repository.InsuranceMembersRepository;
import ru.schoolservice.arm.repository.InsuranceRepository;
import ru.schoolservice.arm.repository.MemberRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private ClaimRepository claimRepository;
    private MemberRepository memberRepository;
    private InsuranceRepository insuranceRepository;
    private InsuranceMembersRepository insuranceMembersRepository;


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



    public void addMembersToInsurance(Insurance insurance, List<Member> members) {

        if (members == null || members.isEmpty()) return;

        final Integer insuranceId = insurance.getId();

        List<Integer> memberIds = members.stream()
                .map(Member::getId)
                .collect(Collectors.toList());

        // Проверка существующих связей
        List<InsuranceMembers> existingLinks = insuranceMembersRepository
                .findByInsuranceIdAndMemberIdIn(insuranceId, memberIds);

        Set<Integer> existingMemberIds = existingLinks.stream()
                .map(InsuranceMembers::getMemberId)
                .collect(Collectors.toSet());

        // Создание новых связей
        List<InsuranceMembers> toSave = members.stream()
                .filter(m -> !existingMemberIds.contains(m.getId()))
                .map(m -> new InsuranceMembers(null, insuranceId, m.getId()))
                .collect(Collectors.toList());

        if (!toSave.isEmpty()) {
            insuranceMembersRepository.saveAll(toSave);
        }
    }
}
