package ru.schoolservice.arm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"user"})
public class Vehicle extends BaseEntity {

    public Vehicle(String serial) {
        this.serial = serial;
    }

    @Column(name = "serial")
    private String serial;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
