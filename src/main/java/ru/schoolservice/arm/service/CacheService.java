package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.CacheRepository;

@Service
@AllArgsConstructor
//@NoArgsConstructor
public class CacheService {

    private CacheRepository cacheRepository;

    @Transactional
    public Cache getCache(Cache cache, Checker checker) {
        //User user = cache.getUser();
        return checker.smart(cache);
    }
}
