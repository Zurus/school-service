package ru.schoolservice.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskDto {
    private Integer id;
    private String name;
    private Integer memberId;
}
