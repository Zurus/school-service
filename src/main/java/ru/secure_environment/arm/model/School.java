package ru.secure_environment.arm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.secure_environment.arm.model.common.BaseSerialEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schools")
@Data
@Entity
public class School extends BaseSerialEntity implements Serializable {

    @NotBlank
    @Size(max = 64)
    @Column(name = "name", nullable = false)
    protected String name;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Classes> classes = new HashSet<>();

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
