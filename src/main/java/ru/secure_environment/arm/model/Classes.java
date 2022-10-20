package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
@Data
@Entity
public class Classes extends NamedEntity implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_teacher_id", referencedColumnName = "id")
    private ClassroomTeacher classroomTeacher;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private List<User> users;
}
