package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import ru.secure_environment.arm.model.Role;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.EnumSet;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


/**
 * Do not use {@link org.springframework.data.rest.webmvc.RepositoryRestController (BasePathAwareController}
 * Bugs:
 * NPE with http://localhost:8080/api/account<br>
 * <a href="https://github.com/spring-projects/spring-hateoas/issues/434">data.rest.base-path missed in HAL links</a><br>
 * <a href="https://jira.spring.io/browse/DATAREST-748">Two endpoints created</a>
 * <p>
 * RequestMapping("/${spring.data.rest.basePath}/account") give "Not enough variable values"
 */
@RestController
@RequestMapping(value = AccountController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Account Controller")
public class AccountController {
    public static final String URL = "/api/account";

    private final UserRepository userRepository;
    private final UserMapper USER_MAPPER;
    private final UserListMapper USER_LIST_MAPPER;


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        userRepository.deleteExisted(id);
    }

    @GetMapping("/users")
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

//    @GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public List<UserDto> getUsers() {
//        return USER_LIST_MAPPER.toUserDtoList(userRepository.getAllBy());
//    }
}
