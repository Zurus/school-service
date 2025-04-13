package ru.schoolservice.arm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "timur_id")
    private Integer timurId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cache> caches = new ArrayList<>();
//
    public void add(Cache cache) {
        if (caches == null) {
            caches = new ArrayList<>();
        }
        caches.add(cache);
        cache.setUser(this);
    }
}