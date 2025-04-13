package ru.schoolservice.arm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TimurDto extends BaseDto {
    private String name;
    private List<UserDto> users;
}
