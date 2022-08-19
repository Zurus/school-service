package ru.schoolservice.arm;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.schoolservice.arm.model.Role;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.EnumSet;

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
@RequestMapping(value = AccountController.URL)
@AllArgsConstructor
@Slf4j
@Tag(name = "Account Controller")
public class AccountController implements RepresentationModelProcessor<RepositoryLinksResource> {
    public static final String URL = "/api/account";

    private static final RepresentationModelAssemblerSupport<User, EntityModel<User>> ASSEMBLER =
            new RepresentationModelAssemblerSupport<User, EntityModel<User>>(AccountController.class, (Class<EntityModel<User>>) (Class<?>) EntityModel.class) {
                @Override
                public EntityModel<User> toModel(User entity) {
                    return EntityModel.of(entity, linkTo(AccountController.class).withSelfRel());
                }
            };


    private final UserRepository userRepository;

    /**
     * Получить информацию по пользователю
     *
     * @param authUser авторизованный пользователь
     * @return информация
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<User> get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return ASSEMBLER.toModel(authUser.getUser());
    }

    /**
     * Удалить пользователя
     *
     * @param authUser авторизованнный пользователь
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        userRepository.deleteById(authUser.id());
    }

    /**
     * Зарегистрировать пользователя
     *
     * @param user новый пользователь
     * @return результат регистрации
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<EntityModel<User>> register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        ValidationUtil.checkNew(user);
        user.setRoles(EnumSet.of(Role.USER));
        user = userRepository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/account")
                .build().toUri();

        return ResponseEntity.created(uriOfNewResource).body(ASSEMBLER.toModel(user));
    }

    /**
     * Изменить информацию по пользователю
     *
     * @param user     обновленный пользователь
     * @param authUser авторизованный пользователь
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} to {}", authUser, user);
        User oldUser = authUser.getUser();
        ValidationUtil.assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userRepository.save(user);
    }

    /*
    @GetMapping(value = "/pageDemo", produces = MediaTypes.HAL_JSON_VALUE)
    public PagedModel<EntityModel<User>> pageDemo(Pageable page, PagedResourcesAssembler<User> pagedAssembler) {
        Page<User> users = userRepository.findAll(page);
        return pagedAssembler.toModel(users, ASSEMBLER);
    }
*/

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(AccountController.class).withRel("account"));
        return resource;
    }
}
