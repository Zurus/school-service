package ru.secure_environment.arm.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.mapping.UserMapper;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.CardKeyUtil;
import ru.secure_environment.arm.util.UserUtil;

import java.util.List;

import static ru.secure_environment.arm.util.TextUtil.idWasNotFound;
import static ru.secure_environment.arm.util.validation.ValidationUtil.assureIdConsistent;
import static ru.secure_environment.arm.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void delete(int id) {
        userRepository.deleteExisted(id);
    }

    public User getUser(int id) {
        log.info("find user by id {}", id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(id))
        );
        return user;
    }


    @Transactional
    public User createNewUser(UserDto userDto) {
        log.info("creating new user {}", userDto);
        User user = userMapper.toModel(userDto);
        final String cardIdHex = CardKeyUtil.toHexString(userDto.getCardId());
        checkNew(user);
        return prepareAndSave(user);
    }

    @Transactional
    public void updateUser(UserDto userDto, int id) {
        log.info("updating user {}", userDto);
        User user = userMapper.toModel(userDto);
        final String cardIdHex = CardKeyUtil.toHexString(userDto.getCardId());
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }
}
