package ru.secure_environment.arm.util.validation;

/**
 * Корректность события Event
 */
public enum EventDtoRequirementComplianceEnum {
    CORRECT,
    INCORRECT;

    public static EventDtoRequirementComplianceEnum getEventDtoRequirementComplianceEnum(boolean invalid) {
        if (invalid) {
            return INCORRECT;
        } else {
            return CORRECT;
        }
    }
}
