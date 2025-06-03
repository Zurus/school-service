package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.InsuranceMembers;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.repository.InsuranceMembersRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InsuranceMemberService {

    private final InsuranceMembersRepository insuranceMembersRepository;

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


    public List<Member> getMembersByInsurance(Insurance insurance) {
        return insuranceMembersRepository.findMembersByInsuranceId(insurance.getId());
    }

}
