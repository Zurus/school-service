package ru.secure_environment.arm.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.secure_environment.arm.dto.UserDto;

import static ru.secure_environment.arm.MockData.userDto;
import static ru.secure_environment.arm.UserTestUtil.USER_MATCHER_DTO;

@SpringBootTest
@ActiveProfiles("test")
public class UserDtoMapperTest {

    @Test
    public void convertToDto() {
        String jsonString = JsonUtil.writeValue(userDto);
        UserDto userDtoFromJson = JsonUtil.readValue(jsonString, UserDto.class);
        USER_MATCHER_DTO.assertMatch(userDtoFromJson, userDto);
    }
}
