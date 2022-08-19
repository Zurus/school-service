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
import static ru.schoolservice.arm.UserTestUtil.ADMIN_ID;
import static ru.schoolservice.arm.UserTestUtil.ADMIN_MAIL;
import static ru.schoolservice.arm.UserTestUtil.USER_ID;
import static ru.schoolservice.arm.UserTestUtil.USER_MAIL;
import static ru.schoolservice.arm.UserTestUtil.admin;
import static ru.schoolservice.arm.UserTestUtil.jsonMatcher;
import static ru.schoolservice.arm.UserTestUtil.user;
import static ru.schoolservice.arm.util.ExceptionTextUtil.idWasNotFound;
import static ru.schoolservice.arm.util.JsonUtil.writeValue;

public class UserControllerTest extends AbstractControllerTest {
    static final String URL = "/api/users/";

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(user, UserTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(UserTestUtil::assertUsers);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "search/by-email?email=" + ADMIN_MAIL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(admin, UserTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + USER_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(ADMIN_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        User newUser = UserTestUtil.getNew();
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(newUser, UserTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        User updated = UserTestUtil.getUpdated();
        perform(MockMvcRequestBuilders.put(URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());
        UserTestUtil.assertEquals(updated, userRepository.findById(USER_ID).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(USER_ID))
        ));
    }
}

