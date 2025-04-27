package ru.schoolservice.arm.cps.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum InsuranceRiskObjectType {
    BORROWER("BORROWER", "Заемщик/созаемщик"),

    PLEDGED_OBJECT("PLEDGED_OBJECT", "Объект залога любой");

    private final String value;
    private final String description;

    InsuranceRiskObjectType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * значение поля description
     *
     * @return значение поля description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Получить значение енума из строки
     *
     * @param text строковое представление значения енума
     * @return значение енума
     */
    @JsonCreator
    public static InsuranceRiskObjectType fromValue(String text) {
        return Arrays.stream(InsuranceRiskObjectType.values())
                .filter(candidate -> candidate.value.equals(text))
                .findFirst()
                .orElse(null);
    }
    /**
     * Получить значение енума по значению
     *
     * @param description строковое представление значения енума
     * @return значение енума
     */
    public static InsuranceRiskObjectType fromDescription(String description) {
        return Arrays.stream(InsuranceRiskObjectType.values())
                .filter(candidate -> candidate.description.equals(description))
                .findFirst()
                .orElse(null);
    }
}
