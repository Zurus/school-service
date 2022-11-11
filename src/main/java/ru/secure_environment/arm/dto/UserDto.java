package ru.secure_environment.arm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.secure_environment.arm.model.enums.Role;
import ru.secure_environment.arm.util.validation.HasEmailAndContactsAndId;
import ru.secure_environment.arm.util.validation.HasSchool;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable, HasEmailAndContactsAndId {

    public UserDto(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.email = builder.getEmail();
        this.schoolId = builder.getSchoolId();
        this.cardId = builder.getCardId();
        this.classNumber = builder.getClassNumber();
        this.roles = builder.getRoles();
        this.password = builder.getPassword();
        this.position = builder.getPosition();
        this.contacts = builder.getContacts();
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

    private String classNumber;

    private String position;

    private Set<Role> roles;

    @NotNull
    private List<ContactDto> contacts;

//    @Override
//    public String toString() {
//        return "UserDto:" + id + "[" + email + "]";
//    }


    @Getter
    public static class Builder {

        private Integer id;

        private String name;

        private String email;

        private String schoolId;

        private String cardId;

        private String classNumber;

        private String position;

        private String password;

        private Set<Role> roles;

        private List<ContactDto> contacts;

        public Builder(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder contacts(List<ContactDto> contacts) {
            this.contacts = contacts;
            return this;
        }

        public Builder contacts(Integer id, String phoneNumber, String telegram) {
            return contacts(new ContactDto(id, phoneNumber, telegram));
        }

        public Builder contacts(String phoneNumber, String telegram) {
            return contacts(new ContactDto(null, phoneNumber, telegram));
        }

        public Builder contacts(ContactDto contactDto) {
            this.contacts = Arrays.asList(contactDto);
            return this;
        }

        public Builder addContact(Integer id, String phoneNumber, String telegram) {
            return addContact(new ContactDto(id, phoneNumber, telegram));
        }

        public Builder addContact(String phoneNumber, String telegram) {
            return addContact(new ContactDto(null, phoneNumber, telegram));
        }

        public Builder addContact(ContactDto contactDto) {
            this.contacts.add(contactDto);
            return this;
        }

        public Builder email(String email) {
            this.email = email;
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
