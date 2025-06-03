package ru.schoolservice.arm.Mock;

import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.dto.MemberDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.Member;

import java.util.Arrays;
import java.util.List;

public class Mock {

    public static Member returnMember1() {
        Member mem1 = new Member(null, "sys1", 1);
        return mem1;
    }

    public static Member returnMember2() {
        Member mem2 = new Member(null, "sys2", 1);
        return mem2;
    }


    // Для DTO
    public static MemberDto createMemberDto1() {
        return new MemberDto(1, 1, "Member Value 1");
    }

    public static MemberDto createMemberDto2() {
        return new MemberDto(2, 1, "Member Value 2");
    }

    public static RiskDto createRiskDto1() {
        return new RiskDto(201, "High Risk", "Risk Value 1");
    }

    public static RiskDto createRiskDto2() {
        return new RiskDto(202, "Low Risk", "Risk Value 2");
    }

    public static InsuranceDto createInsuranceDto() {
        return new InsuranceDto(
                301,
                "Insurance Value",
                4001,
                Arrays.asList(createMemberDto1(), createMemberDto2()),
                Arrays.asList(createRiskDto1(), createRiskDto2())
        );
    }

    public static InsuranceDto createInsuranceDtoWithoutLists() {
        return new InsuranceDto(
                302,
                "Empty Insurance",
                4002,
                null,
                null
        );
    }

    public static ClaimDto createClaimDto() {
        return new ClaimDto(
                401,
                "Main Claim",
                Arrays.asList(createInsuranceDto(), createInsuranceDtoWithoutLists())
        );
    }

    public static ClaimDto createSimpleClaimDto() {
        return new ClaimDto(
                402,
                "Simple Claim",
                null
        );
    }

    // Коллекции
    public static List<MemberDto> createMemberDtoList() {
        return Arrays.asList(
                new MemberDto(111, 2001, "List Member 1"),
                new MemberDto(112, 2002, "List Member 2"),
                new MemberDto(113, 2003, "List Member 3")
        );
    }

    public static List<RiskDto> createRiskDtoList() {
        return Arrays.asList(
                new RiskDto(211, "List Risk 1", "Value A"),
                new RiskDto(212, "List Risk 2", "Value B"),
                new RiskDto(213, "List Risk 3", "Value C")
        );
    }

    public static List<InsuranceDto> createInsuranceDtoList() {
        return Arrays.asList(
                new InsuranceDto(321, "Insurance A", 5001,
                        createMemberDtoList().subList(0, 2),
                        createRiskDtoList().subList(0, 1)),

                new InsuranceDto(322, "Insurance B", 5002,
                        null,
                        createRiskDtoList().subList(1, 3))
        );
    }
}

