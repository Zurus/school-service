package ru.schoolservice.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDto {
    private Integer id;
    private String value;
    private Integer claimId;
    List<MemberDto> members;
    List<RiskDto> riskDtos;
}
