package ru.secure_environment.arm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import ru.secure_environment.arm.util.JsonUtil;

@Configuration
@Slf4j
@EnableCaching
public class AppConfig {

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder
//                .setConnectTimeout(Duration.ofMillis(3000))
//                .setReadTimeout(Duration.ofMillis(3000))
//                .build();
//    }

    @Autowired
    void setMapper(ObjectMapper objectMapper) {
        JsonUtil.setObjectMapper(objectMapper);
    }
}
