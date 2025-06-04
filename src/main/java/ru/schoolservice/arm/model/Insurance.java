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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurances")
@Getter
@Setter
@ToString
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "claim_id")
    private Integer claimId;

    @OneToMany(
            mappedBy = "insurance",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Risks> risks = new ArrayList<>();

    // Вспомогательный метод для управления связью
    public void addRisk(Risks risk) {
        risks.add(risk);
        risk.setInsurance(this);
    }
}