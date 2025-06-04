package ru.schoolservice.arm.converters;

import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.dto.MemberDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.model.Risks;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    // Claim Converter
    public static ClaimDto convertToDto(Claim claim, List<InsuranceDto> insurances) {
        ClaimDto dto = new ClaimDto();
        dto.setId(claim.getId());
        dto.setName(claim.getName());
        dto.setList(insurances);
        return dto;
    }

    public static Claim convertToEntity(ClaimDto dto) {
        Claim entity = new Claim();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    // Insurance Converter
    public static InsuranceDto convertToDto(
            Insurance insurance,
            List<MemberDto> members,
            List<RiskDto> risks
    ) {
        InsuranceDto dto = new InsuranceDto();
        dto.setId(insurance.getId());
        dto.setValue(insurance.getValue());
        dto.setClaimId(insurance.getClaimId());
        dto.setMembers(members);
        dto.setRiskDtos(risks);
        return dto;
    }

    public static Insurance convertToEntity(InsuranceDto dto) {
        Insurance entity = new Insurance();
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        entity.setClaimId(dto.getClaimId());
        return entity;
    }

    // Member Converter
    public static MemberDto convertToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setClaimId(member.getClaimId());
        dto.setValue(member.getValue());
        return dto;
    }

    public static Member convertToEntity(MemberDto dto) {
        Member entity = new Member();
        entity.setId(dto.getId());
        entity.setClaimId(dto.getClaimId());
        entity.setValue(dto.getValue());
        return entity;
    }

    // Risk Converter
    public static RiskDto convertToDto(Risks risk) {
        RiskDto dto = new RiskDto();
        dto.setId(risk.getId());
        dto.setName(risk.getName());
        dto.setMemberValue(risk.getMember() != null ? risk.getMember().getValue() : null);
        return dto;
    }

    public static Risks convertToEntity(RiskDto dto, Member member) {
        Risks entity = new Risks();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setMember(member);
        return entity;
    }

    // Helper methods for collections
    public static List<InsuranceDto> convertInsurancesToDtos(
            List<Insurance> insurances,
            List<MemberDto> allMembers,
            List<RiskDto> allRisks
    ) {
        return insurances.stream()
                .map(insurance -> {
                    List<MemberDto> members = filterMembersByInsurance(allMembers, insurance.getId());
                    List<RiskDto> risks = filterRisksByInsurance(allRisks, insurance.getId());
                    return convertToDto(insurance, members, risks);
                })
                .collect(Collectors.toList());
    }

    private static List<MemberDto> filterMembersByInsurance(List<MemberDto> members, Integer insuranceId) {
        return members.stream()
                .filter(m -> m.getClaimId() != null && m.getClaimId().equals(insuranceId))
                .collect(Collectors.toList());
    }

    private static List<RiskDto> filterRisksByInsurance(List<RiskDto> risks, Integer insuranceId) {
        return risks.stream()
                .filter(r -> r.getMemberValue() != null) // Фильтр по insuranceId реализуется в сервисе
                .collect(Collectors.toList());
    }
}
