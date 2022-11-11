package ru.secure_environment.arm.model.enums;

public enum NotificationEnum {
    TELEGRAM,
    PHONE_SMS,
    WHATSAPP,
    ALL;

    @Override
    public String toString() {
        return name();
    }
}
