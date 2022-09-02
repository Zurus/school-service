package ru.secure_environment.arm.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.Role;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.JsonUtil;
import ru.secure_environment.arm.util.UserUtil;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.secure_environment.arm.UserTestUtil.ADMIN_ID;
import static ru.secure_environment.arm.UserTestUtil.ADMIN_MAIL;
import static ru.secure_environment.arm.UserTestUtil.NOT_FOUND;
import static ru.secure_environment.arm.UserTestUtil.USER_ID;
import static ru.secure_environment.arm.UserTestUtil.USER_MATCHER;
import static ru.secure_environment.arm.UserTestUtil.admin;
import static ru.secure_environment.arm.UserTestUtil.user;

public class AccountControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AccountController.URL + '/';

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(userRepository.findById(USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(USER_MATCHER.contentJson(admin, user));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        UserDto userDto = new UserDto(null, "newUserName", "newEmail", "newPassword", EnumSet.of(Role.USER));
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDto)))
                .andDo(print())
                .andExpect(status().isNoContent());

        User user1 = userRepository.getById(USER_ID);
        User user2 = UserUtil.updateFromDto(userDto, new User(user));

        USER_MATCHER.assertMatch(userRepository.getById(USER_ID), UserUtil.updateFromDto(userDto, new User(user)));
    }


//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                // https://jira.spring.io/browse/SPR-14472
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(USER_MATCHER.contentJson(admin));
//    }
//
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        assertFalse(userRepository.findById(USER_ID).isPresent());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void deleteNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void update() throws Exception {
//        User updated = getUpdated();
//        updated.setId(null);
//        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonWithPassword(updated, "newPass")))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        USER_MATCHER.assertMatch(userRepository.getById(USER_ID), getUpdated());
//    }


}
