package ru.secure_environment.arm.model.enums;

import ru.secure_environment.arm.error.IllegalRequestDataException;

public enum EventEnum {
    EXIT(1),
    ENTER(2),
    EMPTY(3);

    private int status;

    EventEnum(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name();
    }

    public static EventEnum parse(int id) {
        for (EventEnum item : EventEnum.values()) {
            if (item.getStatus() == id) {
                return item;
            }
        }

        throw new IllegalRequestDataException("Event direction didn't found direction = " + id);
    }

    public static String getTextDirection(EventEnum eventEnum) {
        switch (eventEnum) {
            case EXIT:
                return "вышел";
            case ENTER:
                return "вошел";
            default:
                return "неизвестно";
        }
    }

    public int getStatus() {
        return status;
    }
}
