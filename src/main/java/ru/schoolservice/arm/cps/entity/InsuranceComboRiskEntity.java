package ru.schoolservice.arm.cps.entity;


import javax.persistence.*;

@Entity
@Table(name = "insurance_combo_risk")
public class InsuranceComboRiskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insurance_combo_credit_risk_id")
    private InsuranceCreditRiskEntity comboRisk;

    @ManyToOne
    @JoinColumn(name = "insurance_combined_credit_risk_id")
    private InsuranceCreditRiskEntity combinedRisk;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsuranceCreditRiskEntity getComboRisk() {
        return comboRisk;
    }

    public void setComboRisk(InsuranceCreditRiskEntity comboRisk) {
        this.comboRisk = comboRisk;
    }

    public InsuranceCreditRiskEntity getCombinedRisk() {
        return combinedRisk;
    }

    public void setCombinedRisk(InsuranceCreditRiskEntity combinedRisk) {
        this.combinedRisk = combinedRisk;
    }
}
