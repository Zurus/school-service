package ru.schoolservice.arm.service;


import ru.schoolservice.arm.model.User;

import java.util.List;


public interface UserService {

    User create(User user);

    User createOrReturnCached(User user);

    User createAndRefreshCache(User user);

    User create(String name, String email);

    User get(Integer id);

    List<User> getAll();

    void delete(Integer id);

    void deleteAndEvict(Integer id);
}
