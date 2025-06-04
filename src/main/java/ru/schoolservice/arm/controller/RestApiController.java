package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.dto.ClaimDto;
import ru.schoolservice.arm.dto.InsuranceDto;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.model.Member;
import ru.schoolservice.arm.service.DbService;
import ru.schoolservice.arm.service.InsuranceMemberService;

import java.util.List;

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


    //
    private void scenario1() {
        System.out.println("*******************1111**************************");
        Claim claim = dbService.getClaimById(1);
        System.out.println("*******************1111**************************");
        List<Member> members = dbService.getMembers(claim.getId());
        System.out.println("*******************1111**************************");

        ClaimDto claimDto = createClaimDto();
        InsuranceDto insuranceDto = claimDto.getList().stream().findFirst().get();



    }

}
