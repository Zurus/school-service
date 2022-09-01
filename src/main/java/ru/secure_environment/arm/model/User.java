package ru.secure_environment.arm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import ru.secure_environment.arm.util.JsonDeserializers;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity implements Serializable {

    public User(Integer id, String email, String name, String password, Set<Role> roles) {
        this(email, name, password, roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles));
        this.id = id;
    }

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    private String email;

    @Column(name = "name")
    @Size(max = 128)
    @NoHtml
    private String name;

    @Column(name = "password")
    @Size(max = 256)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = JsonDeserializers.PasswordDeserializer.class)
    private String password;

    @Enumerated(EnumType.STRING)
    //https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/collection-table.html
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}