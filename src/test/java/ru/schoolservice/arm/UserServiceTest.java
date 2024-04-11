package ru.schoolservice.arm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.service.UserService;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 31.08.19
 * Time: 10:59
 * e-mail: slava_rossii@list.ru
 */
@Slf4j
public class UserServiceTest extends AbstractTest {

    @Autowired
    private UserService service;

    @Test
    public void get() {
        User user1 = service.create(new User("Vasya", "vasya@mail.ru"));
        User user2 = service.create(new User("Kolya", "kolya@mail.ru"));

        log.info("************************************************************************************");
        log.info("*************************************** start ***************************************");
        log.info("");
        log.info("");
        log.info("");
        log.info("");


        log.info("Запрашиваем пользователя 1");
        getAndPrint(user1.getId());
        log.info("Запрашиваем пользователя 2");
        getAndPrint(user2.getId());
        log.info("Запрашиваем пользователя 1");
        getAndPrint(user1.getId());
        log.info("Запрашиваем пользователя 2");
        getAndPrint(user2.getId());


        log.info("");
        log.info("");
        log.info("");
        log.info("************************************************************************************");
        log.info("*************************************** stop ***************************************");
    }

    @Test
    public void create() {
        createAndPrint("Ivan", "ivan@mail.ru");
        createAndPrint("Ivan", "ivan1122@mail.ru");
        createAndPrint("Sergey", "ivan@mail.ru");

        log.info("all entries are below:");
        service.getAll().forEach(u -> log.info("{}", u.toString()));
    }

    @Test
    public void createAndRefresh() {
        User user1 = service.createOrReturnCached(new User("Vasya", "vasya@mail.ru"));
        log.info("created user1: {}", user1);

        User user2 = service.createOrReturnCached(new User("Vasya", "misha@mail.ru"));
        log.info("created user2: {}", user2);

        User user3 = service.createAndRefreshCache(new User("Vasya", "kolya@mail.ru"));
        log.info("created user3: {}", user3);

        User user4 = service.createOrReturnCached(new User("Vasya", "petya@mail.ru"));
        log.info("created user4: {}", user4);
    }

    @Test
    public void delete() {
        User user1 = service.create(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user1.getId()));

        User user2 = service.create(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user2.getId()));

        service.delete(user1.getId());
        service.deleteAndEvict(user2.getId());

        log.info("{}", service.get(user1.getId()));
        log.info("{}", service.get(user2.getId()));
    }

    @Test
    public void checkSettings() throws InterruptedException {
        User user1 = service.createOrReturnCached(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user1.getId()));

        User user2 = service.createOrReturnCached(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user2.getId()));

        Thread.sleep(1000L);
        User user3 = service.createOrReturnCached(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user3.getId()));
    }

    private void getAndPrint(Integer id) {
        log.info("Пользователь найден: {}", service.get(id));
    }

    private void createAndPrint(String name, String email) {
        log.info("created user: {}", service.create(name, email));
    }
}
