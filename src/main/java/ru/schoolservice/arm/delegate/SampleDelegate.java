package ru.schoolservice.arm.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class SampleDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(SampleDelegate.class);

    @Autowired
    private RuntimeService runtimeService;


    @PostConstruct
    public void init() {
        logger.info("=== SampleDelegate инициализирован как Spring Bean ===");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
//        logger.info("=== SampleDelegate запущен ===");
        logger.info("=== SampleDelegate запущен ===");
        for (int i = 0; i < 10; i++) {
            String businessKey = String.format("load-test-%s", i);
            runtimeService.startProcessInstanceByKey("RunnableProcess", businessKey);
        }
    }
}
