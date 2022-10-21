package ru.secure_environment.arm.model.enums;

public enum MessageEnum {
    TELEGRAM();

    @Override
    public String toString() {
        return name();
    }
}
