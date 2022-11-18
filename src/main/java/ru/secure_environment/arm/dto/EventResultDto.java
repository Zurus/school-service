package ru.secure_environment.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.secure_environment.arm.model.enums.EventEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EventResultDto {

    private String textDate;
    private String cardId;
    private EventEnum direction;
}
