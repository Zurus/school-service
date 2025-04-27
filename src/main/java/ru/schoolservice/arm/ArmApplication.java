package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.schoolservice.arm.cps.service.InsuranceProgramsRiskDelegate;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class ArmApplication implements ApplicationRunner {

    private final InsuranceProgramsRiskDelegate insuranceProgramsRiskDelegate;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insuranceProgramsRiskDelegate.findRisksByInsuranceCode("И1", true)
                .stream()
                .forEach(el -> log.info(el.toString()));
    }

}