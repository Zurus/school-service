package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.cps.service.InsuranceProgramRiskService;
import ru.schoolservice.arm.dto.CacheDto;
import ru.schoolservice.arm.dto.UserDto;
import ru.schoolservice.arm.mapper.ToEntity;
import ru.schoolservice.arm.mapper.toDto;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.Timur;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.CacheRepository;
import ru.schoolservice.arm.repository.ServiceApplication;
import ru.schoolservice.arm.repository.TimurRepository;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.service.CacheService;
import ru.schoolservice.arm.service.Checker;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication implements ApplicationRunner {

    private final InsuranceProgramRiskService insuranceProgramRiskService;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}