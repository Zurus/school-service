package ru.secure_environment.arm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.secure_environment.arm.model.enums.Role;
import ru.secure_environment.arm.util.validation.HasSchool;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {

    public UserDto(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.email = builder.getEmail();
        this.phoneNumber = builder.getPhoneNumber();
        this.schoolId = builder.getSchoolId();
        this.cardId = builder.getCardId();
        this.telegram = builder.getTelegram();
        this.classNumber = builder.getClassNumber();
        this.roles = builder.getRoles();
        this.password = builder.getPassword();
        this.position = builder.getPosition();
    }

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @Email
    @NoHtml
    private String email;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Wrong phone number")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @NotBlank
    @HasSchool
    private String schoolId;

    @NotNull
    @NotBlank
    private String cardId;

    private String telegram;

    private String classNumber;

    private String position;

    private Set<Role> roles;

    @Override
    public String toString() {
        return "UserDto:" + id + "[" + email + "]";
    }

    @Getter
    public static class Builder {

        private Integer id;

        private String name;

        private String email;

        private String phoneNumber;

        private String schoolId;

        private String cardId;

        private String telegram;

        private String classNumber;

        private String position;

        private String password;

        private Set<Role> roles;

        public Builder(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder schoolId(String schoolId) {
            this.schoolId = schoolId;
            return this;
        }

        public Builder cardId(String cardId) {
            this.cardId = cardId;
            return this;
        }

        public Builder telegram(String telegram) {
            this.telegram = telegram;
            return this;
        }


        public Builder classNumber(String classNumber) {
            this.classNumber = classNumber;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder position(String position) {
            this.position = position;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
