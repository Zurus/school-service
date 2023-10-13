package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Notification extends BaseEntity {

    @Column(name = "text", nullable = false)
    @NotBlank
    private String text;

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "notification_contact",
//            joinColumns = @JoinColumn(name = "notification_id", foreignKey = @ForeignKey(name = "notification_contact_notification_id_fk")),
//            inverseJoinColumns = @JoinColumn(name = "contact_id", foreignKey = @ForeignKey(name="notification_contact_contact_id")),
//            uniqueConstraints = @UniqueConstraint(name = "notification_contact_uk", columnNames = {"notification_id", "contact_id"})
//    )
//    private List<Contact> contacts = new ArrayList<>();
}
