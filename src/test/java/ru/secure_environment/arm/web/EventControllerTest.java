package ru.secure_environment.arm.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static ru.secure_environment.arm.UserTestUtil.ADMIN_MAIL;
import static ru.secure_environment.arm.UserTestUtil.eventString;
import static ru.secure_environment.arm.web.EventController.URL;

class EventControllerTest extends AbstractControllerTest {


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {

        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventString))
                .andDo(print());
        //.andExpect(status().isCreated());

//        School created = SCHOOL_MATCHER.readFromJson(action);
//        SCHOOL_MATCHER.assertMatch(created, school);
    }
}