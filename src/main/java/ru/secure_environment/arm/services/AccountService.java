package ru.secure_environment.arm.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.mapping.UserMapper;
import ru.secure_environment.arm.model.Card;
import ru.secure_environment.arm.model.Classes;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.CardRepository;
import ru.secure_environment.arm.repository.SchoolClassRepository;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.CardKeyUtil;
import ru.secure_environment.arm.util.UserUtil;

import java.util.List;

import static ru.secure_environment.arm.util.ExceptionTextUtil.idWasNotFound;
import static ru.secure_environment.arm.util.validation.ValidationUtil.assureIdConsistent;
import static ru.secure_environment.arm.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final SchoolClassRepository classRepository;
    private final CardRepository cardRepository;


    public void delete(int id) {
        userRepository.deleteExisted(id);
    }

    public User getUser(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(id))
        );
        return user;
    }

    @Transactional
    public List<User> getUsersFromSchool(String id) {
        return userRepository.findUserBySchoolClass(id);
    }

    @Transactional
    public User createNewUser(UserDto userDto) {
        User user = userMapper.toModel(userDto);
        Classes classes = classRepository.findClassesByIdAndName(userDto.getSchoolId(), userDto.getClassNumber()).orElseThrow(
                () -> new IllegalRequestDataException("cannot find class schoolId=" + userDto.getSchoolId() + " classNumber = " + userDto.getClassNumber())
        );
        final String cardIdHex = CardKeyUtil.toHexString(userDto.getCardId());
        Card card = cardRepository.findCardByCardId(cardIdHex).orElseThrow(
                () -> new IllegalRequestDataException("cannot find card=" + cardIdHex)
        );
        user.setSchoolClass(classes);
        user.setCard(card);
        checkNew(user);
        return prepareAndSave(user);
    }

    @Transactional
    public void updateUser(UserDto userDto, int id) {
        User user = userMapper.toModel(userDto);
        Classes classes = classRepository.findClassesByIdAndName(userDto.getSchoolId(), userDto.getClassNumber()).orElseThrow(
                () -> new IllegalRequestDataException("cannot find class schoolId=" + userDto.getSchoolId() + " classNumber = " + userDto.getClassNumber())
        );
        user.setSchoolClass(classes);
        final String cardIdHex = CardKeyUtil.toHexString(userDto.getCardId());
        Card card = cardRepository.findCardByCardId(cardIdHex).orElseThrow(
                () -> new IllegalRequestDataException("cannot find card=" + cardIdHex)
        );
        user.setCard(card);
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    private User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }
}
