package ru.schoolservice.arm.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.service.DbService;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
public class RestApiController {

    public final static String REQUEST = "/get";

    @Autowired
    public RestApiController(DbService dbService) {
        this.dbService = dbService;
    }

    private DbService dbService;

    private String KEY;

    @RequestMapping(value = REQUEST, method = RequestMethod.GET)
    public String getUsers() {
        return KEY;
    }

    //Загружаем параметр из БД
    @PostConstruct
    public void init() {
        KEY = dbService.getKey();
    }
}
