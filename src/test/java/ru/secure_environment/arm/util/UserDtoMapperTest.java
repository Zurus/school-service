package ru.secure_environment.arm.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.secure_environment.arm.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.secure_environment.arm.MockData.userDto;
import static ru.secure_environment.arm.UserTestUtil.USER_MATCHER_DTO;

@SpringBootTest
@ActiveProfiles("test")
public class UserDtoMapperTest {

    private static final String jsonWithoutPassword = "{\"id\":1,\"name\":\"User\",\"email\":\"user@gmail.com\",\"phoneNumber\":\"+79026165214\",\"schoolId\":\"A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6\",\"cardId\":\"056,35766\",\"telegram\":\"asdf\",\"classNumber\":\"1A\",\"roles\":[\"USER\"]}";

    private static final String jsonString = "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"User\",\n" +
            "  \"email\": \"user@gmail.com\",\n" +
            "  \"phoneNumber\": \"+79026165214\",\n" +
            "  \"password\": \"asdfadsf\",\n" +
            "  \"schoolId\": \"A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6\",\n" +
            "  \"cardId\": \"056,35766\",\n" +
            "  \"telegram\": \"asdf\",\n" +
            "  \"classNumber\": \"1A\",\n" +
            "  \"roles\": [\n" +
            "    \"USER\"\n" +
            "  ]\n" +
            "}\n";

    @Test
    public void convertToJsonTest() {
        String json = JsonUtil.<UserDto>writeValue(userDto);
        assertThat(json).isEqualTo(jsonWithoutPassword);
    }


    @Test
    public void convertToDto() {
        UserDto userDtoFromJson = JsonUtil.readValue(jsonString, UserDto.class);
        USER_MATCHER_DTO.assertMatch(userDtoFromJson, userDto);
    }
}
