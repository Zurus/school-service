package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classroom_teachers")
@Data
@Entity
public class ClassroomTeacher extends BaseEntity implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Classes schoolClass;
}
