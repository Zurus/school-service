package ru.schoolservice.arm.web;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.schoolservice.arm.UserTestUtil;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.schoolservice.arm.AccountController.URL;
import static ru.schoolservice.arm.UserTestUtil.ADMIN_ID;
import static ru.schoolservice.arm.UserTestUtil.USER_ID;
import static ru.schoolservice.arm.UserTestUtil.USER_MAIL;
import static ru.schoolservice.arm.UserTestUtil.asUser;
import static ru.schoolservice.arm.UserTestUtil.jsonMatcher;
import static ru.schoolservice.arm.UserTestUtil.user;
import static ru.schoolservice.arm.util.ExceptionTextUtil.idWasNotFound;
import static ru.schoolservice.arm.util.JsonUtil.writeValue;

public class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(user, UserTestUtil::assertEquals));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(ADMIN_ID).isPresent());
    }

    @Test
    void register() throws Exception {
        User newUser = UserTestUtil.getNew();
        User registered = asUser(perform(MockMvcRequestBuilders.post(URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated()).andReturn());

        int newId = registered.id();
        newUser.setId(newId);
        UserTestUtil.assertEquals(registered, newUser);
        UserTestUtil.assertEquals(registered, userRepository.findById(newId).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(USER_ID))
        ));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        User updated = UserTestUtil.getUpdated();
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTestUtil.assertEquals(updated, userRepository.findById(USER_ID).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(USER_ID))
        ));
    }
}
