package ru.secure_environment.arm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.enums.EventEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventResultDto {

    private int confirmedLogId;

    public EventResultDto(int confirmedLogId) {
        this.confirmedLogId = confirmedLogId;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cardId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EventEnum direction;

    public void confirmIfBigger(int logId) {
        if (logId > confirmedLogId) {
            confirmedLogId = logId;
        }
    }
}
