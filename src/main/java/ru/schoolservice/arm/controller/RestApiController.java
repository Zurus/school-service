package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.Mock.Mock;
import ru.schoolservice.arm.converters.Converter;
import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.dto.MemberDto;
import ru.schoolservice.arm.dto.RiskDto;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.model.Risks;
import ru.schoolservice.arm.service.DbService;
import ru.schoolservice.arm.service.InsuranceMemberService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.schoolservice.arm.Mock.Mock.createClaimDto;

@RestController
@Slf4j
@AllArgsConstructor
public class RestApiController {
    public final static String REQUEST = "/get";

    private DbService dbService;
    private InsuranceMemberService insuranceMemberService;


    @RequestMapping(value = REQUEST, method = RequestMethod.GET)
    public String getUsers() {
//        System.out.println("*******************1111**************************");
//        Claim claim = dbService.getClaimWithInsuranceById(1);
//        System.out.println("*******************2222**************************");
//        Insurance insurance = claim.getInsurances().stream().findFirst().get();
//        System.out.println("*******************3333**************************");
//        List<Member> mem = insuranceMemberService.getMembersByInsurance(insurance);
//        System.out.println("*******************4444**************************");
        int switcher = 1;

        if (switcher == 1) {
            scenario1();
        }
        return "KEY";
    }

    @Transactional
    public void scenario1() {
        log.info("Processing claim...");

        final int claimId = 1;
        Claim claim = dbService.getClaimById(claimId);
        Map<String, Member> memberMap = dbService.getMembers(claimId).stream()
                .collect(Collectors.toMap(Member::getValue, m -> m));

        ClaimDto claimDto = Mock.createClaimDto(); // Получение DTO из внешнего источника

        for (InsuranceDto insuranceDto : claimDto.getList()) {
            Insurance insurance = createInsuranceWithRisks(insuranceDto, memberMap);
            dbService.saveInsuranceWithRisks(insurance);
        }
    }

    private Insurance createInsuranceWithRisks(InsuranceDto dto, Map<String, Member> memberMap) {
        Insurance insurance = new Insurance();
        insurance.setValue(dto.getValue());
        insurance.setClaimId(dto.getClaimId());

        for (RiskDto riskDto : dto.getRiskDtos()) {
            Member member = memberMap.get(riskDto.getMemberValue());
            if (member == null) {
                log.warn("Member not found: {}", riskDto.getMemberValue());
                continue;
            }

            Risks risk = new Risks();
            risk.setName(riskDto.getName());
            risk.setMember(member);
            insurance.addRisk(risk); // Автоматически устанавливает связь
        }
        return insurance;
    }
}
