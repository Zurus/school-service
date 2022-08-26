package ru.secure_environment.arm.web;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.secure_environment.arm.UserTestUtil;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.secure_environment.arm.UserTestUtil.USER_MAIL;
import static ru.secure_environment.arm.util.JsonUtil.writeValue;
import static ru.secure_environment.arm.web.AccountController.URL;
import static ru.secure_environment.arm.util.ExceptionTextUtil.idWasNotFound;

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
                .andExpect(UserTestUtil.jsonMatcher(UserTestUtil.user, UserTestUtil::assertNoIdEquals));
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
        Assertions.assertFalse(userRepository.findById(UserTestUtil.USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.ADMIN_ID).isPresent());
    }

    @Test
    void register() throws Exception {
        User newUser = UserTestUtil.getNew();
        User registered = UserTestUtil.asUser(perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated()).andReturn());

        UserTestUtil.assertNoIdEquals(registered, newUser);
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

        UserTestUtil.assertEquals(updated, userRepository.findById(UserTestUtil.USER_ID).orElseThrow(
                () -> new UsernameNotFoundException(idWasNotFound(UserTestUtil.USER_ID))
        ));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafe() throws Exception {
        User updated = UserTestUtil.getUpdated();
        updated.setFirstName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
