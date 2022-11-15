package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.service.DbService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value="/api")
public class RestApiController {

    private DbService dbService;

    private final String KEY = dbService.getKey();

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers() {
        return KEY;
    }
}
