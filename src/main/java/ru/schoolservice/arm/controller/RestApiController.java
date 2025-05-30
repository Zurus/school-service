package ru.schoolservice.arm.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.model.Claim;
import ru.schoolservice.arm.model.Insurance;
import ru.schoolservice.arm.service.DbService;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Slf4j
public class RestApiController {

    public final static String REQUEST = "/get";

    @Autowired
    public RestApiController(DbService dbService) {
        this.dbService = dbService;
    }

    private DbService dbService;


    @RequestMapping(value = REQUEST, method = RequestMethod.GET)
    public String getUsers() {
        //dbService.getKey();
        System.out.println("*************************************************");
        Claim claim = dbService.getClaimById(1);
        String val = claim.getName();
        List<Insurance> insuranceList = claim.getInsurances();
        return "KEY";
    }
}
