package ru.secure_environment.arm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.secure_environment.arm.model.enums.Role;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getPhoneNumber(), u.getCardId(), u.getTelegram(), u.getRoles());
    }

    public User(Integer id, String name, String email, String password, String phoneNumber,
                String cardId, String telegram, Role role, Role... roles) {
        this(id, name, email, password, phoneNumber, cardId, telegram, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, String phoneNumber,
                String cardId, String telegram, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cardId = cardId;
        this.telegram = telegram;
        setRoles(roles);
    }


    @Column(name = "card_id", nullable = false)
    @NotBlank
    //@Pattern(regexp = "[0-9]{3},[0-9]{5}", message = "Wrong card id")
    private String cardId;

    @Column(name = "phone_number", nullable = false)
    //@NotNull
    //@NotBlank
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Wrong phone number")
    private String phoneNumber;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    //@NotBlank
    @Size(max = 128)
    @NoHtml
    private String email;

    @Column(name = "password")
    @Size(max = 256)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", nullable = false)
    private Classes schoolClass;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Event> visits;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_messages",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "message_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Message> messages;

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
        setIfNotNull(this::setCardId, user.getCardId());
        setIfNotNull(this::setTelegram, user.getTelegram());
        setIfNotNull(this::setRoles, user.getRoles());
        return this;
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}