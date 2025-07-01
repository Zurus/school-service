package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.Mock.Mock;
import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.InsuranceContractDataEntity;
import ru.schoolservice.arm.model.InsuranceContractOperClaims;
import ru.schoolservice.arm.model.InsuranceDetailsDataEntity;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.service.DbService;
import ru.schoolservice.arm.service.InsuranceMemberService;

import java.util.Map;
import java.util.stream.Collectors;

import static ru.schoolservice.arm.Mock.Mock.createRiskDtoList;
import static ru.schoolservice.arm.converters.Converter.convertToEntity;

@RestController
@Slf4j
@AllArgsConstructor
public class RestApiController {
    public final static String REQUEST = "/get";

    private DbService dbService;
    private InsuranceMemberService insuranceMemberService;

    @RequestMapping(value = REQUEST, method = RequestMethod.GET)
    public String getUsers() {
        scenario1();
        return "KEY";
    }

    public void scenario1() {
        log.info("Processing claim...");

        final int claimId = 1;
        InsuranceContractOperClaims claim = dbService.getClaimWithInsuranceById(claimId);


        ClaimDto claimDto = Mock.createClaimDto(); // Получение DTO из внешнего источника

        InsuranceContractDataEntity insuranceContractDataEntity = convertToEntity(Mock.createInsuranceDto());

        Map<Integer, Member> memberMap = dbService.getMembers(claimId).stream()
                .collect(Collectors.toMap(Member::getId, m -> m));

        createRiskDtoList().stream()
                .map(riskDto -> buildInsuranceDetailsDataEntity(riskDto, memberMap.get(riskDto.getMemberId()), insuranceContractDataEntity))
                .collect(Collectors.toList());


    }


    public static InsuranceDetailsDataEntity buildInsuranceDetailsDataEntity(RiskDto riskDto,
                                                                             Member member,
                                                                             InsuranceContractDataEntity insuranceContractDataEntity) {
        InsuranceDetailsDataEntity insuranceDetailsDataEntity = convertToEntity(riskDto);
        insuranceDetailsDataEntity.setMember(member);
        insuranceDetailsDataEntity.setInsuranceContractDataEntity(insuranceContractDataEntity);
        return insuranceDetailsDataEntity;
    }
}
