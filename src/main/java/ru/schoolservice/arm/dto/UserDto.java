package ru.schoolservice.arm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {
    private String email;
    private Integer timurId;
    private List<CacheDto> caches;
}
