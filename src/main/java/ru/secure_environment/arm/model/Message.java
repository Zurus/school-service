package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.secure_environment.arm.model.common.BaseEntity;
import ru.secure_environment.arm.model.enums.MessageEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Message extends BaseEntity implements Serializable {

    @Column(name = "text", nullable = false)
    @NotBlank
    private String text;

    @ManyToMany(mappedBy = "messages", fetch = FetchType.LAZY)
    private Set<User> users;

    @Enumerated(EnumType.ORDINAL)
    //https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/collection-table.html
    @Column(name = "message_type")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MessageEnum messageEnum;
}
