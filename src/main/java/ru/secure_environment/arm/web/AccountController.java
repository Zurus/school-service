package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.mapping.UserListMapper;
import ru.secure_environment.arm.mapping.UserMapper;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.services.AccountService;
import ru.secure_environment.arm.web.user.UniqueContactsUserValidator;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = AccountController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Account Controller")
public class AccountController {
    public static final String URL = "/api/account";

    private final UserMapper userMapper;
    private final UserListMapper userListMapper;
    private final AccountService accountService;
    private final UniqueContactsUserValidator contactsValidator;

    //https://www.concretepage.com/spring/spring-mvc/spring-mvc-custom-validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(contactsValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable int id) {
        log.info("get {}", id);
        User user = accountService.getUser(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        accountService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserDto user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        accountService.updateUser(user, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        log.info("create new user {}", userDto);
        User created = accountService.createNewUser(userDto);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(userMapper.toDTO(created));
    }

    @GetMapping(value = "/school/{id}")
    public ResponseEntity<List<UserDto>> getUsers(@PathVariable String id) {
        log.info("find all by school {}", id);
        return ResponseEntity.ok(userListMapper.toUserListDto(accountService.getUsersFromSchool(id)));
    }
}
