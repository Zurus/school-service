package ru.secure_environment.arm.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.secure_environment.arm.model.School;
import ru.secure_environment.arm.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.secure_environment.arm.MockData.ADMIN_MAIL;
import static ru.secure_environment.arm.UserTestUtil.SCHOOL_MATCHER;
import static ru.secure_environment.arm.UserTestUtil.getNewSchool;
import static ru.secure_environment.arm.web.SchoolController.URL;

class SchoolControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {

        School school = getNewSchool();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(school)))
                .andDo(print())
                .andExpect(status().isCreated());

        School created = SCHOOL_MATCHER.readFromJson(action);
        SCHOOL_MATCHER.assertMatch(created, school);
    }
}