package ru.schoolservice.arm.Mock;

import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.dto.MemberDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.Member;

import java.util.Arrays;
import java.util.List;

public class Mock {

    private static Integer CLAIM_ID = 1;
    private static String CLAIM_NAME = "claim name";

    private static Integer MEMBER_ID_1 = 1;
    private static Integer MEMBER_ID_2 = 2;

    private static String MEMBER_VALUE_1 = "Иванов Иван";
    private static String MEMBER_VALUE_2 = "Петров Петр";

    private static Integer INSURANCE_CLAIM_1 = 1;
    private static String INSURANCE_CLAIM_NAME_1 = "Договор 1";

    private static Integer RISK_ID_1 = 1;
    private static Integer RISK_ID_2 = 2;

    private static String RISK_NAME_1 = "Risk 1";
    private static String RISK_NAME_2 = "Risk 2";

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
        return new MemberDto(MEMBER_ID_1, CLAIM_ID, MEMBER_VALUE_1);
    }

    public static MemberDto createMemberDto2() {
        return new MemberDto(MEMBER_ID_2, CLAIM_ID, MEMBER_VALUE_2);
    }

    public static RiskDto createRiskDto1() {
        return new RiskDto(RISK_ID_1, RISK_NAME_1, MEMBER_VALUE_1);
    }

    public static RiskDto createRiskDto2() {
        return new RiskDto(RISK_ID_2, RISK_NAME_2, MEMBER_VALUE_2);
    }

    public static InsuranceDto  createInsuranceDto() {
        return new InsuranceDto(
                INSURANCE_CLAIM_1,
                INSURANCE_CLAIM_NAME_1,
                CLAIM_ID,
                Arrays.asList(createMemberDto1(), createMemberDto2()),
                Arrays.asList(createRiskDto1(), createRiskDto2())
        );
    }

    public static InsuranceDto  createNewInsuranceDto() {
        return new InsuranceDto(
                INSURANCE_CLAIM_1,
                INSURANCE_CLAIM_NAME_1,
                CLAIM_ID,
                Arrays.asList(createMemberDto1(), createMemberDto2()),
                Arrays.asList(createRiskDto1(), createRiskDto2())
        );
    }

    public static InsuranceDto createInsuranceDtoWithoutLists() {
        return new InsuranceDto(
                INSURANCE_CLAIM_1,
                INSURANCE_CLAIM_NAME_1,
                CLAIM_ID,
                null,
                null
        );
    }

    public static ClaimDto createClaimDto() {
        return new ClaimDto(
                CLAIM_ID,
                CLAIM_NAME,
                Arrays.asList(createNewInsuranceDto())
        );
    }

    public static ClaimDto createSimpleClaimDto() {
        return new ClaimDto(
                CLAIM_ID,
                CLAIM_NAME,
                null
        );
    }

    // Коллекции
    public static List<MemberDto> createMemberDtoList() {
        return Arrays.asList(
                createMemberDto1(),
                createMemberDto2()
        );
    }

    public static List<RiskDto> createRiskDtoList() {
        return Arrays.asList(
                createRiskDto1(),
                createRiskDto2()
        );
    }

    public static List<InsuranceDto> createInsuranceDtoList() {
        return Arrays.asList(
                createInsuranceDto()
        );
    }
}

