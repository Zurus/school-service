package ru.secure_environment.arm.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.secure_environment.arm.model.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    public UserDto(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.email = builder.getEmail();
        this.phoneNumber = builder.getPhoneNumber();
        this.schoolId = builder.getSchoolId();
        this.cardId = builder.getCardId();
        this.telegram = builder.getTelegram();
        this.isEmployee = builder.getIsEmployee();
        this.classNumber = builder.getClassNumber();
        this.jobTitle = builder.getJobTitle();
        this.classRoomTeacher = builder.getClassRoomTeacher();
        this.roles = builder.getRoles();
    }

    private Integer id;

    private String name;

    private String email;

    private String phoneNumber;

    private String schoolId;

    private String cardId;

    private String telegram;

    private Boolean isEmployee;

    private String classNumber;

    private String jobTitle;

    private Boolean classRoomTeacher;

    private Set<Role> roles;

    @Getter
    public static class Builder {

        private Integer id;

        private String name;

        private String email;

        private String phoneNumber;

        private String schoolId;

        private String cardId;

        private String telegram;

        private Boolean isEmployee;

        private String classNumber;

        private String jobTitle;

        private Boolean classRoomTeacher;

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

        public Builder isEmployee(Boolean isEmployee) {
            this.isEmployee = isEmployee;
            return this;
        }

        public Builder classNumber(String classNumber) {
            this.classNumber = classNumber;
            return this;
        }

        public Builder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Builder classRoomTeacher(Boolean classRoomTeacher) {
            this.classRoomTeacher = classRoomTeacher;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
