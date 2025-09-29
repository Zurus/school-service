package ru.schoolservice.arm.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProcessControlller {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private ApplicationContext applicationContext;


    @GetMapping("/beans")
    public Map<String, Object> getAllBeans() {
        Map<String, Object> beansInfo = new HashMap<>();

        // Получаем все имена бинов
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        // Сортируем имена для удобства чтения
        Arrays.sort(beanNames);

        // Собираем информацию о каждом бине
        List<Map<String, String>> beans = Arrays.stream(beanNames)
                .map(beanName -> {
                    Map<String, String> beanInfo = new HashMap<>();
                    beanInfo.put("beanName", beanName);

                    Object bean = applicationContext.getBean(beanName);
                    beanInfo.put("beanType", bean.getClass().getName());
                    return beanInfo;
                })
                .collect(Collectors.toList());

        beansInfo.put("totalBeans", beans.size());
        beansInfo.put("beans", beans);

        return beansInfo;
    }


    @PostMapping("/start-process")
    public Map<String, Object> startProcess(@RequestBody(required = false) Map<String, Object> variables) {
        // Запуск процесса по ключу
        ProcessInstance instance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("SampleProcess", variables);

        Map<String, Object> response = new HashMap<>();
        response.put("processInstanceId", instance.getId());
        response.put("businessKey", instance.getBusinessKey());
        response.put("variables", variables);

        return response;
    }
}
