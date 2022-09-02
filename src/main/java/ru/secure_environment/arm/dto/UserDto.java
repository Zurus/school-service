package ru.secure_environment.arm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.secure_environment.arm.model.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private Set<Role> roles;
}
