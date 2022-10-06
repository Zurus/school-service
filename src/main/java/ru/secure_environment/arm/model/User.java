package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends NamedEntity implements Serializable {

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getPhoneNumber(), u.getSchoolId(),
                u.getCardId(), u.getTelegram(), u.getIsEmployee(), u.getClassNumber(), u.getJobTitle(), u.getClassRoomTeacher(), u.getRoles());
    }

    public User(Integer id, String name, String email, String password, String phoneNumber,
                String schoolId, String cardId, String telegram, Boolean isEmployee, String classNumber, String jobTitle, Boolean classRoomTeacher, Role role, Role... roles) {
        this(id, name, email, password, phoneNumber, schoolId, cardId, telegram, isEmployee, classNumber, jobTitle, classRoomTeacher, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, String phoneNumber,
                String schoolId, String cardId, String telegram, Boolean isEmployee, String classNumber, String jobTitle, Boolean classRoomTeacher, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.schoolId = schoolId;
        this.cardId = cardId;
        this.telegram = telegram;
        this.isEmployee = isEmployee;
        this.classNumber = classNumber;
        this.jobTitle = jobTitle;
        this.classRoomTeacher = classRoomTeacher;
        setRoles(roles);
    }

    @Column(name = "school_id", nullable = false)
    @NotBlank
    @NotNull
    @Pattern(regexp = "[0-9]{1,6}-[0-9]{1,4}", message = "Wrong school id")
    private String schoolId;

    @Column(name = "card_id", nullable = false)
    @NotNull
    @NotBlank
    @Pattern(regexp = "[0-9]{3},[0-9]{5}", message = "Wrong card id")
    private String cardId;

    @Column(name = "phone_number", nullable = false)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Wrong phone number")
    private String phoneNumber;

    @Column(name = "telegram")
    private String telegram;

    /**
     * Является ли пользователь сотрудником
     * true - сотрудник
     * false - ученик
     */
    @Column(name = "is_emloyee")
    @NotNull
    private Boolean isEmployee;

    /**
     * Класс
     * актуально для ученика
     */
    @Column(name = "class_number")
    private String classNumber;

    /**
     * Должность
     * актуально для сотрудника
     */
    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "class_room_teacher")
    private Boolean classRoomTeacher;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    private String email;


    @Column(name = "password")
    @Size(max = 256)
    private String password;

    @Enumerated(EnumType.STRING)
    //https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/collection-table.html
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @Column(name = "photo")
    @Size(max = 1024)
    private byte[] photo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Events> visits;

    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public User updateUser(User user) {
        setIfNotNull(this::setPassword, user.getPassword());
        setIfNotNull(this::setEmail, user.getEmail());
        setIfNotNull(this::setName, user.getName());
        setIfNotNull(this::setPhoneNumber, user.getPhoneNumber());
        setIfNotNull(this::setSchoolId, user.getSchoolId());
        setIfNotNull(this::setCardId, user.getCardId());
        setIfNotNull(this::setTelegram, user.getTelegram());
        setIfNotNull(this::setIsEmployee, user.getIsEmployee());
        setIfNotNull(this::setClassNumber, user.getClassNumber());
        setIfNotNull(this::setJobTitle, user.getJobTitle());
        setIfNotNull(this::setClassRoomTeacher, user.getClassRoomTeacher());
        setIfNotNull(this::setRoles, user.getRoles());
        return this;
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}