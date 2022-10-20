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

    private int logId;
    private String time;
    private String keyHex;
    private int direction;
}
