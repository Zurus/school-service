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
    /*
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }*/


//    @Profile("dev")
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    Server h2Server() throws SQLException {
//        log.info("Start H2 TCP server");
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//    }

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
