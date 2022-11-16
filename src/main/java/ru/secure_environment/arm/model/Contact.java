package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.secure_environment.arm.model.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
@Entity
@Data
@ToString(exclude = {"user"})
public class Contact extends BaseEntity {

    public Contact(Integer id, String phoneNumber, String telegram) {
        super(id);
        this.phoneNumber = phoneNumber;
        this.telegram = telegram;
        this.chatId = null;
    }

    @ManyToMany(mappedBy = "contacts", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Wrong phone number")
    private String phoneNumber;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "chat_id")
    private String chatId;

    @ManyToMany(mappedBy = "contacts")
    private Set<Notification> notifications = new HashSet<>();
}
