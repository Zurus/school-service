package ru.secure_environment.arm.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.secure_environment.arm.dto.UserDto;

import static ru.secure_environment.arm.UserTestUtil.USER_MATCHER_DTO;
import static ru.secure_environment.arm.UserTestUtil.userDto;

@SpringBootTest
public class UserDtoMapperTest {

//    @Autowired
//    private UserMapper userDtoMapper;

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
        System.out.println(json);
    }


    @Test
    public void convertToDto() {
        UserDto userDtoFromJson = JsonUtil.readValue(jsonString, UserDto.class);
        USER_MATCHER_DTO.assertMatch(userDtoFromJson, userDto);
    }

}
