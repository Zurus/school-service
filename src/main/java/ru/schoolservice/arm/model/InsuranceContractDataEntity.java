package ru.schoolservice.arm.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurances")
@Getter
@Setter
@ToString
public class InsuranceContractDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "claim_id")
    private Integer claimId;
}