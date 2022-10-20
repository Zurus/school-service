package ru.secure_environment.arm.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.mapping.UserMapper;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;

//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment
public class AbstractControllerTest {

    @Autowired
    //https://sysout.ru/testirovanie-kontrollerov-s-pomoshhyu-mockmvc/
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    protected UserMapper userMapper;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    //@Transactional
    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow(
                () -> new IllegalRequestDataException("user with id=" + id + " not found!")
        );
    }
}
