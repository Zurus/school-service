package ru.secure_environment.arm.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.secure_environment.arm.dto.EventResultDto;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.secure_environment.arm.MockData.ADMIN_MAIL;
import static ru.secure_environment.arm.MockData.RESULT_DTO;
import static ru.secure_environment.arm.MockData.eventString;
import static ru.secure_environment.arm.MockData.eventStringAlreadyExisted;
import static ru.secure_environment.arm.UserTestUtil.EVENT_RESULT_DTO_MATCHER;
import static ru.secure_environment.arm.web.EventController.URL;

class EventControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventString))
                .andDo(print())
                .andExpect(status().isOk());

        EventResultDto created = EVENT_RESULT_DTO_MATCHER.readFromJson(action);
        EVENT_RESULT_DTO_MATCHER.assertMatch(RESULT_DTO, created);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create_alreadyExistedEvent() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventStringAlreadyExisted))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}