package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.mapping.UserListMapper;
import ru.secure_environment.arm.mapping.UserMapper;
import ru.secure_environment.arm.model.User;

import javax.validation.Valid;
import java.util.List;

import static ru.secure_environment.arm.util.validation.ValidationUtil.assureIdConsistent;


@RestController
@RequestMapping(value = AccountController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Account Controller")
public class AccountController extends AbstractUserController {
    public static final String URL = "/api/account";

    private final UserMapper userMapper;
    private final UserListMapper userListMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable int id) {
        User user = getUser(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody UserDto userDto, @PathVariable int id) {
        log.info("update {} with id={}", userDto, id);
        User user = new User();
        user.setId(id);
        user.updateFromDto(userDto);
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userListMapper.toUserListDto(repository.getAllBy()));
    }
}
