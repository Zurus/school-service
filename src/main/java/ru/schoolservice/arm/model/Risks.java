package ru.schoolservice.arm.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "risks")
@Getter
@Setter
public class Risks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "name")
    private String name;

    //    @Transient
    //private String memberValue;

    @Column(name = "insurance_program_id")
    private Integer insuranceProgramId;

    //    @Column(name = "member_id")
//    private Integer memberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

}