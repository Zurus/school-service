package ru.schoolservice.arm.service;

import ru.schoolservice.arm.model.Cache;

@FunctionalInterface
public interface Checker {

    Cache smart(Cache cache);
}
