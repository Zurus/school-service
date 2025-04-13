package ru.schoolservice.arm.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Сущность "Данные о страховании УКС"
 *
 * @author bazanova
 * @since 18.04.2019
 */
@Entity
@Table(name = "insurance_data")
public class InsuranceDataRiskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "risk_code")
    private String riskCode;

    @Column(name = "abs_risk_code")
    private String absRiskCode;

    @Column(name = "insurance_rate")
    private BigDecimal insuranceRate;

    @Column(name = "insurance_fee")
    private BigDecimal insuranceFee;

    @ManyToOne
    @JoinColumn(name = "insurance_data_id", nullable = false)
    private InsuranceDataEntity insuranceData;

    /**
     * Получить идентификатор сущности
     *
     * @return идентификатор сущности
     */
    public Long getId() {
        return id;
    }

    /**
     * Установить идентификатор сущности
     *
     * @param id - идентификатор сущности
     */
    public void setId(Long id) {
        this.id = id;
    }


    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getAbsRiskCode() {
        return absRiskCode;
    }

    public void setAbsRiskCode(String absRiskCode) {
        this.absRiskCode = absRiskCode;
    }

    public BigDecimal getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(BigDecimal insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public InsuranceDataEntity getInsuranceData() {
        return insuranceData;
    }

    public void setInsuranceData(InsuranceDataEntity insuranceData) {
        this.insuranceData = insuranceData;
    }
}
