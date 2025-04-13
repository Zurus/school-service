package ru.schoolservice.arm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CacheDto extends BaseDto {
    private String cache;
    private Integer userId;
}
