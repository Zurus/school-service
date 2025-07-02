package ru.schoolservice.arm.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id")
    private InsuranceContractOperClaims claimId;

    @OneToMany(
            mappedBy = "insuranceContractDataEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<InsuranceDetailsDataEntity> risks = new HashSet<>();


    public void addRisk(InsuranceDetailsDataEntity risk) {
        risks.add(risk);
        risk.setInsuranceContractDataEntity(this);
    }
}