package ru.secure_environment.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventResultDto {

    private int confirmedLogId;

    public void confirmIfBigger(int logId) {
        if (logId > confirmedLogId) {
            confirmedLogId = logId;
        }
    }
}
