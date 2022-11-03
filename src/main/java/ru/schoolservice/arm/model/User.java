package ru.schoolservice.arm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
//@ToString(exclude = {"list"})
@ToString
public class User extends BaseEntity {

    public User(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 128)
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private List<Vehicle> list = new ArrayList<>();

    public boolean addVehicle(Vehicle vehicle) {
        vehicle.setUser(this);
        return list.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        list.remove(vehicle);
        vehicle.setUser(null);
    }
}