package ru.schoolservice.arm.converters;

import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.dto.MemberDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.InsuranceContractDataEntity;
import ru.schoolservice.arm.model.InsuranceContractOperClaims;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.model.InsuranceDetailsDataEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    // Claim Converter
    public static ClaimDto convertToDto(InsuranceContractOperClaims insuranceContractOperClaims, List<InsuranceDto> insurances) {
        ClaimDto dto = new ClaimDto();
        dto.setId(insuranceContractOperClaims.getId());
        dto.setName(insuranceContractOperClaims.getName());
        dto.setList(insurances);
        return dto;
    }

    public static InsuranceContractOperClaims convertToEntity(ClaimDto dto) {
        InsuranceContractOperClaims entity = new InsuranceContractOperClaims();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    // Insurance Converter
    public static InsuranceDto convertToDto(
            InsuranceContractDataEntity insuranceContractDataEntity,
            List<MemberDto> members,
            List<RiskDto> risks
    ) {
        InsuranceDto dto = new InsuranceDto();
        dto.setId(insuranceContractDataEntity.getId());
        dto.setValue(insuranceContractDataEntity.getValue());
        dto.setClaimId(insuranceContractDataEntity.getClaimId().getId());
        dto.setMembers(members);
        dto.setRiskDtos(risks);
        return dto;
    }

    public static InsuranceContractDataEntity convertToEntity(InsuranceDto dto) {
        InsuranceContractDataEntity entity = new InsuranceContractDataEntity();
        //entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        //entity.setClaimId(dto.getClaimId());
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
    public static RiskDto convertToDto(InsuranceDetailsDataEntity risk) {
        RiskDto dto = new RiskDto();
        dto.setId(risk.getId());
        dto.setName(risk.getName());
        //dto.setMemberValue(risk.getMember() != null ? risk.getMember().getValue() : null);
        return dto;
    }

    public static InsuranceDetailsDataEntity convertToEntity(RiskDto dto) {
        InsuranceDetailsDataEntity entity = new InsuranceDetailsDataEntity();
        //entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    // Helper methods for collections
    public static List<InsuranceDto> convertInsurancesToDtos(
            List<InsuranceContractDataEntity> insuranceContractDataEntities,
            List<MemberDto> allMembers,
            List<RiskDto> allRisks
    ) {
        return insuranceContractDataEntities.stream()
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
                .filter(r -> r.getMemberId() != null) // Фильтр по insuranceId реализуется в сервисе
                .collect(Collectors.toList());
    }
}
