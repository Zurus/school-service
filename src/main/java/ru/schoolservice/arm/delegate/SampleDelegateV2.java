package ru.schoolservice.arm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class SampleDelegateV2 implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(SampleDelegate.class);
    private static int count = 0;

    public SampleDelegateV2() {
        count++;
        logger.debug("***Создан объект SampleDelegateV2 count = {} ***", count);
    }

    @PostConstruct
    public void postInit() {
        logger.debug("*** post construct SampleDelegateV2 ***");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.debug("Выполнение SampleDelegateV2 (экземпляр делегата: {})", count);
        execution.setVariable("processed", true);
    }
}
