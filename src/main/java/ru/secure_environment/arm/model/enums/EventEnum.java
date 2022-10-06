package ru.secure_environment.arm.model.enums;

public enum EventEnum {
    EMPTY(-1),
    ENTER(0),
    EXIT(1);

    private int status;

    EventEnum(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name();
    }

    public static EventEnum parse(int id) {
        EventEnum status = null; // Default
        for (EventEnum item : EventEnum.values()) {
            if (item.getStatus() == id) {
                status = item;
                break;
            }
        }
        return status;
    }

    public int getStatus() {
        return status;
    }
}
