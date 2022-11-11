package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.common.NamedEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
@Data
@Entity
public class Classes extends NamedEntity  {

    //Классный руководитель
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_teacher_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "classes_user_id_fk"))
    private User classRoomTeacher;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id", nullable = false, foreignKey = @ForeignKey(name = "classes_school_id_fk"))
    private School school;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private List<User> users;
}
