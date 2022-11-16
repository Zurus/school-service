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
import ru.secure_environment.arm.util.validation.NoContacts;
import ru.secure_environment.arm.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "users_email_uk", columnNames = {"email"}))
@Getter
@Setter
public class User extends NamedEntity {

    public User(User u) {
        this(u.getId(), u.getName(), u.getPassword(), u.getEmail(), u.getCard(), u.getContacts(), u.getRoles());
    }

    public User(Integer id, String name, String password, String email,
                Card card, List<Contact> contacts, Role role, Role... roles) {
        this(id, name, password, email, card, contacts, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, String email, Card card,
                List<Contact> contacts, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.withNotifications = false;
        this.contacts = contacts;
        this.password = password;
        this.card = card;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", nullable = false, foreignKey = @ForeignKey(name = "user_classes_id_fk"))
    private Classes schoolClass;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "card_id", nullable = false, foreignKey = @ForeignKey(name = "user_card_id_fk"))
    private Card card;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "user_contacts",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_contacts_user_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "contact_id", foreignKey = @ForeignKey(name = "user_contacts_contact_id_fk")),
            uniqueConstraints = @UniqueConstraint(name = "user_contact_uk", columnNames = {"user_id", "contact_id"}))
    @NoContacts
    private List<Contact> contacts = new ArrayList<>();

    @Column(name="with_notifications", nullable = false)
    private boolean withNotifications;

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.getUsers().add(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.getUsers().remove(contact);
    }

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
        setIfNotNull(this::setContacts, user.getContacts());
        return this;
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}