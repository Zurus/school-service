package ru.secure_environment.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventDto implements Serializable {

    /**
     * Идентификатор события
     */
    private int logId;

    /**
     * Время возникновения события
     */
    private String time;

    /**
     * Номер идентификатор
     */
    private String keyHex;

    /**
     * Направление совершенного прохода
     * 1 = выход
     * 2 = вход
     * 3 = неизвестное
     */
    private int direction;

    /**
     * Id объекта доступа в базе системы
     * Равно 0, если объект отсутствует в базе СИГУР'а
     */
    private int internalEmpId;
}
