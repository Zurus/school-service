package ru.schoolservice.arm.cps.entity;

import ru.schoolservice.arm.cps.model.InsuranceRiskObjectType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "insurance_credit_risk")
public class InsuranceCreditRiskEntity extends DictionaryEntity {

    @Column(name = "object_type", length = 50)
    @Enumerated(value = EnumType.STRING)
    private InsuranceRiskObjectType objectType;

    @Column(name = "abs_risk_code", length = 50)
    private String absRiskCode;

    @OneToMany(mappedBy = "comboRisk", fetch = FetchType.LAZY)
    private List<InsuranceComboRiskEntity> comboRiskRelations = new ArrayList<>();

    @Column(name = "is_combined")
    private Boolean isCombined;

    /**
     * Конструктор
     */
    public InsuranceCreditRiskEntity() {
    }

    /**
     * Конструктор
     *
     * @param code        код
     * @param name        название
     * @param objectType  тип объекта страхования
     * @param absRiskCode код риска в абс
     */
    public InsuranceCreditRiskEntity(String code, String name, InsuranceRiskObjectType objectType, String absRiskCode) {
        super(code, name);
        this.objectType = objectType;
        this.absRiskCode = absRiskCode;
    }

    /**
     * Получить тип объекта страхования
     *
     * @return тип объекта страхования
     */
    public InsuranceRiskObjectType getObjectType() {
        return objectType;
    }

    /**
     * Установить тип объекта страхования
     *
     * @param objectType тип объекта страхования
     */
    public void setObjectType(InsuranceRiskObjectType objectType) {
        this.objectType = objectType;
    }

    /**
     * Получить тип объекта страхования
     *
     * @return тип объекта страхования
     */
    public String getAbsRiskCode() {
        return absRiskCode;
    }

    /**
     * Установить тип объекта страхования
     *
     * @param absRiskCode тип объекта страхования
     */
    public void setAbsRiskCode(String absRiskCode) {
        this.absRiskCode = absRiskCode;
    }


    public Boolean getCombined() {
        return isCombined;
    }

    public void setCombined(Boolean combined) {
        isCombined = combined;
    }


    public List<InsuranceCreditRiskEntity> getCombinedRisks() {
        if (!Boolean.TRUE.equals(isCombined)) {
            return Collections.emptyList();
        }
        return comboRiskRelations.stream()
                .map(InsuranceComboRiskEntity::getCombinedRisk)
                .collect(Collectors.toList());
    }

}
