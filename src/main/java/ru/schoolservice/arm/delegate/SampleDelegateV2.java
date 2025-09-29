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


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.debug("Выполнение DelegateExecution (экземпляр делегата: {})", 0);
        // Минимальная логика для уменьшения нагрузки
        execution.setVariable("processed", true);
    }
}
