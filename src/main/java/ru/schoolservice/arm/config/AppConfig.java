package ru.schoolservice.arm.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class AppConfig {


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    //TODO заполнить актуальными кредами базы данных
    @Bean
    public ProcessEngine processEngine() {
        return ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcUrl("jdbc:postgresql://10.7.118.29:5432/efr_14")
                .setJdbcDriver("org.postgresql.Driver")
                .setJdbcUsername("accreditive_service_excamad")
                .setJdbcPassword("accreditive_service_excamad")
                .setJobExecutorActivate(true)
                .buildProcessEngine();
    }
}
