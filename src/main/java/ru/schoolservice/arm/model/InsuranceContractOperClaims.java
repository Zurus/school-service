package ru.schoolservice.arm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claims")
@Getter
@Setter
@ToString(exclude = {"insurances"})
public class InsuranceContractOperClaims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(
            mappedBy = "claimId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    ) // Указываем столбец в таблице employees
    private Set<InsuranceContractDataEntity> insuranceContractDataEntities = new HashSet<>();


    public void add(InsuranceContractDataEntity insuranceContractDataEntity) {
        insuranceContractDataEntities.add(insuranceContractDataEntity);
        insuranceContractDataEntity.setClaimId(this);
    }
}