package ru.schoolservice.arm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "caches")
@Getter
@Setter
public class Cache extends BaseEntity {

    @Column(name = "cache", nullable = false)
    private String cache;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "user_id")
//    private Integer userId;

}