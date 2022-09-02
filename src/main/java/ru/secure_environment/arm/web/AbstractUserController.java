package ru.secure_environment.arm.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.UserUtil;

import static ru.secure_environment.arm.util.ExceptionTextUtil.idWasNotFound;

@Slf4j
public class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    public User getUser(int id) {
        log.info("get {}", id);
        User user = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(id))
        );
        return user;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}
