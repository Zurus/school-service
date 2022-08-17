package ru.schoolservice.arm.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.schoolservice.arm.AccountController.URL;
import static ru.schoolservice.arm.UserTestUtil.ADMIN_ID;
import static ru.schoolservice.arm.UserTestUtil.USER_ID;
import static ru.schoolservice.arm.UserTestUtil.USER_MAIL;

public class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE));
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
    @WithUserDetails(value = USER_MAIL)
    void testJson() throws JsonProcessingException {
        User user = userRepository.findById(USER_ID).orElseThrow(
                () -> new UsernameNotFoundException("User '" + USER_ID + "'was not found")
        );
        System.out.println(JsonUtil.writeValue(user));
    }
}