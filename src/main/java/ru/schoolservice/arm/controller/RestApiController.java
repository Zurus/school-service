package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.UserRepository;

import java.util.List;

@RestController
//@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api")
public class RestApiController {

    private UserRepository repository;

    @RequestMapping(value = "/user/{id}")
    //@Transactional
    public ResponseEntity<User> getUser(@PathVariable int id) {
        log.info("Get user id = {}", id);
        return ResponseEntity.ok(repository.findUserById(id).get());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        log.info("Get All users");
        return repository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        log.info("Save user: {}", user);
        user = repository.save(user);
        System.out.println("новый пользак добавлен " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
