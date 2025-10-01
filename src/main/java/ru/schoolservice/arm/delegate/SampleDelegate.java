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


@Component("sampleDelegate")
public class SampleDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(SampleDelegate.class);

    private static int count = 0;

    @Autowired
    private RuntimeService runtimeService;


    public SampleDelegate() {
        count++;
        logger.debug("===Создан объект SampleDelegate count = {} ===", count);
    }

    @PostConstruct
    public void init() {
        logger.info("=== SampleDelegate инициализирован как Spring Bean ===");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("=== SampleDelegate запущен (экземпляр делегата: {})", count);
        for (int i = 0; i < 5; i++) {
            String businessKey = String.format("load-test-%s", i);
            runtimeService.startProcessInstanceByKey("RunnableProcess", businessKey);
        }
    }
}
