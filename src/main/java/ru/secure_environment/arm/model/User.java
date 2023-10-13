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
import ru.secure_environment.arm.model.common.NamedEntity;
import ru.secure_environment.arm.model.enums.Role;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "users_email_uk", columnNames = {"email"}))
@Getter
@Setter
public class User extends NamedEntity {

    public User(User u) {
        this(u.getId(), u.getName(), u.getPassword(), u.getEmail(), u.getRoles());
    }

    public User(Integer id, String name, String password, String email,
               Role role, Role... roles) {
        this(id, name, password, email, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, String email, Collection<Role> roles) {
        this(id,name,password,email,false, roles);
    }

    public User(Integer id, String name, String password, String email, Boolean withNotifications,  Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.withNotifications = withNotifications;
        this.password = password;
        setRoles(roles);
    }

    @Column(name = "email", nullable = false)
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
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_user_id_fk")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_role_uk"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @Column(name = "photo")
    @Size(max = 1024)
    private byte[] photo;



    @Column(name="with_notifications", nullable = false)
    private Boolean withNotifications;


    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public User updateUser(User user) {
        setIfNotNull(this::setPassword, user.getPassword());
        setIfNotNull(this::setName, user.getName());
        setIfNotNull(this::setEmail, user.getEmail());
        setIfNotNull(this::setRoles, user.getRoles());
        return this;
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}